#define MAX_LIGHTS_COUNT 8

uniform mat4 u_NormalMatrix;

uniform sampler2D u_DiffuseTexture;
uniform sampler2D u_AmbientTexture;
uniform sampler2D u_SpecularTexture;

uniform float u_Shininess;

uniform int u_LightsCount;
uniform int u_LightType[MAX_LIGHTS_COUNT];
uniform vec4 u_LightColor[MAX_LIGHTS_COUNT];

varying vec3 v_LightVector[MAX_LIGHTS_COUNT];
varying float v_LightPower[MAX_LIGHTS_COUNT];

varying vec3 v_VertexPosition;
varying vec4 v_VertexNormal;
varying vec2 v_TextureCoordinates;

float positiveDot(vec3 a, vec3 b) {
	return max(0.0, dot(a, b));
}

float positiveDot(vec4 a, vec4 b) {
	return max(0.0, dot(a, b));
}

void main() {
	vec3 camera = normalize(-v_VertexPosition);
	vec3 normal = normalize((u_NormalMatrix * v_VertexNormal).xyz);

	vec3 diffuseLightValue = vec3(0.0, 0.0, 0.0);
	vec3 specularLightValue = vec3(0.0, 0.0, 0.0);

	for (int index = 0; index < u_LightsCount; index++) {
		vec3 lightVector = v_LightVector[index];
		vec3 halfVector = normalize(vec3(camera + lightVector));
		diffuseLightValue += u_LightColor[index].rgb * positiveDot(normal, lightVector) * v_LightPower[index];
		specularLightValue += u_LightColor[index].rgb * pow(positiveDot(halfVector, normal), u_Shininess * (2.0 - v_LightPower[index]));
	}

	vec4 ambientColor = texture2D(u_AmbientTexture, v_TextureCoordinates);
	vec4 diffuseColor = texture2D(u_DiffuseTexture, v_TextureCoordinates);
	vec4 specularColor = texture2D(u_SpecularTexture, v_TextureCoordinates);

	vec3 ambient = ambientColor.rgb;
	vec3 diffuse = diffuseColor.rgb * diffuseLightValue;
	vec3 specular = specularColor.rgb * specularLightValue;

	gl_FragColor = vec4(specular + diffuse + ambient, diffuseColor.a);
}
