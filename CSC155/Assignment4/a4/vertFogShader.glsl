#version 430

layout (location=0) in vec3 vertPos;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 vertNormal;

uniform float alpha;
uniform float flipNormal;

out vec3 vertEyeSpacePos;
out vec2 tc;
out vec3 vNormal;

uniform mat4 norm_matrix;
uniform mat4 mv_matrix;
uniform mat4 proj_matrix;
layout (binding=0) uniform sampler2D t;	// for texture
layout (binding=1) uniform sampler2D h;	// for height map

void main(void)
{	// height-mapped vertex
	vec4 p = vec4(vertPos,1.0) + vec4((vertNormal*((texture2D(h,texCoord).r)/5.0f)),1.0f);

		//get a vertex normal vector in eye space and output it to the rasterizer for interpolation
	vNormal = (norm_matrix * vec4(vertNormal,1.0)).xyz;
	
	//if rendering a back-face, flip the normal
	if (flipNormal < 0) vNormal = -vNormal;

	tc = texCoord;
	
	// compute vertex position in eye space (without perspective)
	vertEyeSpacePos = (mv_matrix * p).xyz;
	gl_Position = proj_matrix * mv_matrix * p;
}
