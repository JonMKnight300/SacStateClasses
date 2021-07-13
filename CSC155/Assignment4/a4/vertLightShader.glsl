#version 430

layout (location = 0) in vec3 vertPos;
layout (location = 1) in vec3 vertNormal;
layout (location = 2) in vec2 texPos;

uniform float alpha;
uniform float flipNormal;

out vec2 tc;

out vec3 varyingNormal;
out vec3 varyingLightDir;
out vec3 varyingVertPos;
out vec3 varyingHalfVector;
out vec4 shadow_coord;
out vec3 vNormal;

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
uniform mat4 shadowMVP;
layout (binding=0) uniform sampler2DShadow shadowTex;

void main(void)
{	varyingVertPos = (mv_matrix * vec4(vertPos,1.0)).xyz;
	varyingLightDir = light.position - varyingVertPos;
	varyingNormal = (norm_matrix * vec4(vertNormal,1.0)).xyz;
	
		//get a vertex normal vector in eye space and output it to the rasterizer for interpolation
	vNormal = (norm_matrix * vec4(vertNormal,1.0)).xyz;
	
		//if rendering a back-face, flip the normal
	if (flipNormal < 0) vNormal = -vNormal;
	
	varyingHalfVector =
		normalize(normalize(varyingLightDir)
		+ normalize(-varyingVertPos)).xyz;

	shadow_coord = shadowMVP * vec4(vertPos,1.0);

	gl_Position = proj_matrix * mv_matrix * vec4(vertPos,1.0);
	    tc = texPos;
}
