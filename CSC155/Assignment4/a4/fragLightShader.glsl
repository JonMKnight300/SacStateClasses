#version 430

in vec2 tc;
in vec3 varyingNormal;
in vec3 varyingLightDir;
in vec3 varyingVertPos;
in vec3 varyingHalfVector;

in vec4 shadow_coord;
out vec4 fragColor;

uniform float alpha;
uniform float flipNormal;

struct PositionalLight
{	vec4 ambient;  
	vec4 diffuse;  
	vec4 specular;  
	vec3 position;
};

struct Material
{	vec4 ambient;  
	vec4 diffuse;  
	vec4 specular;  
	float shininess;
};

uniform vec4 globalAmbient;
uniform PositionalLight light;
uniform Material material;
uniform mat4 mv_matrix;	 
uniform mat4 proj_matrix;
uniform mat4 norm_matrix;
uniform int l;
uniform mat4 shadowMVP;
layout (binding=0)  uniform sampler2D s;
layout (binding=0) uniform sampler2DShadow shadowTex;

float lookup(float x, float y)
{  	float t = textureProj(shadowTex, shadow_coord + vec4(x * 0.001 * shadow_coord.w,
                                                         y * 0.001 * shadow_coord.w,
                                                         -0.01, 0.0));
	return t;
}

void main(void)
{	float shadowFactor=0.0;
	// normalize the light, normal, and view vectors:
	vec3 L = normalize(varyingLightDir);
	vec3 N = normalize(varyingNormal);
	vec3 V = normalize(-varyingVertPos);
	
	// get the angle between the light and surface normal:
	float cosTheta = dot(L,N);
	
	// halfway vector varyingHalfVector was computed in the vertex shader,
	// and interpolated prior to reaching the fragment shader.
	// It is copied into variable H here for convenience later.
	vec3 H = normalize(varyingHalfVector);
	
		float swidth = 2.5;
	vec2 o = mod(floor(gl_FragCoord.xy), 2.0) * swidth;
	shadowFactor += lookup(-1.5*swidth + o.x,  1.5*swidth - o.y);
	shadowFactor += lookup(-1.5*swidth + o.x, -0.5*swidth - o.y);
	shadowFactor += lookup( 0.5*swidth + o.x,  1.5*swidth - o.y);
	shadowFactor += lookup( 0.5*swidth + o.x, -0.5*swidth - o.y);
	shadowFactor = shadowFactor / 4.0;

	float notInShadow = textureProj(shadowTex, shadow_coord);
	
	// get angle between the normal and the halfway vector
	float cosPhi = dot(H,N);

	// compute ADS contributions (per pixel):
	vec3 ambient = ((globalAmbient * material.ambient) + (light.ambient * material.ambient)).xyz;
	vec3 diffuse = light.diffuse.xyz * material.diffuse.xyz * max(cosTheta,0.0);
	vec3 specular = light.specular.xyz * material.specular.xyz * pow(max(cosPhi,0.0), material.shininess*3.0);
	//fragColor = vec4((ambient + diffuse + specular), 1.0);
	//fragColor =  texture(s,tc); //+ 0.8 * (globalAmbient * material.ambient);


	vec4 shadowColor = globalAmbient * material.ambient
				+ light.ambient * material.ambient;
	
	vec4 lightedColor = light.diffuse * material.diffuse * max(dot(L,N),0.0)
				+ light.specular * material.specular
				* pow(max(dot(H,N),0.0),material.shininess*3.0);

//fragColor = 0.3 * texture2D(s,tc) + vec4((shadowColor.xyz + shadowFactor*(lightedColor.xyz)),1.0);

	if (notInShadow == 1.0)
	{	fragColor += 0.3 * texture2D(s,tc) + light.diffuse * material.diffuse * max(dot(L,N),0.0)
				+ light.specular * material.specular
				* pow(max(dot(H,N),0.0),material.shininess*3.0) * vec4((shadowColor.xyz + shadowFactor*(lightedColor.xyz)),1.0);
				
		// the following is added for transparency
		//fragColor = vec4(fragColor.xyz, alpha);
	}
	else
{
	fragColor = 0.3 * texture2D(s,tc) + 0.7 * (globalAmbient * material.ambient
	+ light.ambient * material.ambient
	+ light.diffuse * material.diffuse * max(cosTheta,0.0)
	+ light.specular  * material.specular *
		pow(max(dot(H,N),0.0), material.shininess*3.0) * vec4((shadowColor.xyz + shadowFactor*(lightedColor.xyz)),1.0) );

	// the following is added for transparency
	fragColor = vec4(fragColor.xyz, alpha);
}


	
}
