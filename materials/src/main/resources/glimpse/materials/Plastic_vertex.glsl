#define MAX_LIGHTS_COUNT 8

#define DIRECTION_LIGHT 1
#define OMNI_LIGHT 2
#define SPOTLIGHT 3

uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
uniform mat4 u_ModelMatrix;
uniform mat4 u_LightMatrix;

attribute vec4 a_VertexPosition;
attribute vec4 a_VertexNormal;
attribute vec2 a_TextureCoordinates;

uniform int u_LightsCount;
uniform int u_LightType[MAX_LIGHTS_COUNT];
uniform vec4 u_LightDirection[MAX_LIGHTS_COUNT];
uniform vec4 u_LightPosition[MAX_LIGHTS_COUNT];

varying vec3 v_LightVector[MAX_LIGHTS_COUNT];

varying vec3 v_VertexPosition;
varying vec4 v_VertexNormal;

vec3 directionLightVector(int index) {
	return normalize((u_LightMatrix * -u_LightDirection[index]).xyz);
}

vec3 omniLightVector(int index) {
	return normalize((u_LightMatrix * (u_LightPosition[index] - u_ModelMatrix * a_VertexPosition)).xyz);
}

vec3 lightVector(int index) {
	if (DIRECTION_LIGHT == u_LightType[index]) { // Direction light:
		return directionLightVector(index);
	} else if (OMNI_LIGHT == u_LightType[index]) { // Omni light:
		return omniLightVector(index);
	} else if (SPOTLIGHT == u_LightType[index]) { // Spotlight:
		// TODO
	}
	return vec3(0.0, 0.0, 0.0);
}

void main() {
	gl_Position = u_MVPMatrix * a_VertexPosition;

	for (int index = 0; index < u_LightsCount; index++) {
		v_LightVector[index] = lightVector(index);
	}

	v_VertexPosition = (u_MVMatrix * a_VertexPosition).xyz;
	v_VertexNormal = a_VertexNormal;
}
