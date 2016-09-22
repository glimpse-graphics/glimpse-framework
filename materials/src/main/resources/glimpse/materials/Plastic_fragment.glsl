#define MAX_LIGHTS_COUNT 8

uniform mat4 u_NormalMatrix;

uniform vec4 u_DiffuseColor;
uniform vec4 u_AmbientColor;
uniform vec4 u_SpecularColor;

uniform float u_Shininess;

uniform int u_LightsCount;
uniform int u_LightType[MAX_LIGHTS_COUNT];
uniform vec4 u_LightColor[MAX_LIGHTS_COUNT];

varying vec3 v_LightVector[MAX_LIGHTS_COUNT];

varying vec3 v_VertexPosition;
varying vec4 v_VertexNormal;

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
		diffuseLightValue += u_LightColor[index].rgb * positiveDot(normal, lightVector);
		specularLightValue += u_LightColor[index].rgb * pow(positiveDot(halfVector, normal), u_Shininess);
	}

	vec3 ambient = u_AmbientColor.rgb * 0.2;
	vec3 diffuse = u_DiffuseColor.rgb * diffuseLightValue * 0.8;
	vec3 specular = u_SpecularColor.rgb * specularLightValue;

	gl_FragColor = vec4(specular + diffuse + ambient, u_DiffuseColor.a);
}
