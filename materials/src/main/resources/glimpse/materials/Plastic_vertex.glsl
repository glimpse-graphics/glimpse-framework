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
uniform float u_LightDistance[MAX_LIGHTS_COUNT];

varying vec3 v_LightVector[MAX_LIGHTS_COUNT];
varying float v_LightPower[MAX_LIGHTS_COUNT];

varying vec3 v_VertexPosition;
varying vec4 v_VertexNormal;

void setupDirectionLight(int index) {
	v_LightVector[index] = normalize((u_LightMatrix * -u_LightDirection[index]).xyz);
	v_LightPower[index] = 1.0;
}

void setupOmniLight(int index) {
	vec3 lightVector = (u_LightMatrix * (u_LightPosition[index] - u_ModelMatrix * a_VertexPosition)).xyz;
	v_LightVector[index] = normalize(lightVector);
	v_LightPower[index] = max(0.0, (u_LightDistance[index] - length(lightVector)) / u_LightDistance[index]);
}

void setupSpotlight(int index) {
	vec3 lightVector = (u_LightMatrix * (u_LightPosition[index] - u_ModelMatrix * a_VertexPosition)).xyz;
	v_LightVector[index] = normalize(lightVector);
	v_LightPower[index] = max(0.0, (u_LightDistance[index] - length(lightVector)) / u_LightDistance[index]);
}

void setupLight(int index) {
	if (DIRECTION_LIGHT == u_LightType[index]) {
		setupDirectionLight(index);
	} else if (OMNI_LIGHT == u_LightType[index]) {
		setupOmniLight(index);
	} else if (SPOTLIGHT == u_LightType[index]) {
		setupSpotlight(index);
	}
}

void main() {
	gl_Position = u_MVPMatrix * a_VertexPosition;

	for (int index = 0; index < u_LightsCount; index++) {
		setupLight(index);
	}

	v_VertexPosition = (u_MVMatrix * a_VertexPosition).xyz;
	v_VertexNormal = a_VertexNormal;
}
