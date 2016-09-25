#define MAX_LIGHTS_COUNT 8

#define DIRECTION_LIGHT 1
#define OMNI_LIGHT 2
#define SPOTLIGHT 3

uniform mat4 u_LightMatrix;
uniform mat4 u_NormalMatrix;

uniform sampler2D u_DiffuseTexture;
uniform sampler2D u_AmbientTexture;
uniform sampler2D u_SpecularTexture;

uniform float u_Shininess;

uniform int u_LightsCount;
uniform int u_LightType[MAX_LIGHTS_COUNT];
uniform vec4 u_LightDirection[MAX_LIGHTS_COUNT];
uniform vec4 u_LightPosition[MAX_LIGHTS_COUNT];
uniform float u_LightDistance[MAX_LIGHTS_COUNT];
uniform float u_LightAngleCos[MAX_LIGHTS_COUNT];
uniform vec4 u_LightColor[MAX_LIGHTS_COUNT];

varying vec3 v_VertexPosition;
varying vec4 v_VertexModelPosition;
varying vec4 v_VertexNormal;
varying vec2 v_TextureCoordinates;

vec3 lightVector;
float lightPower;

float positiveDot(vec3 a, vec3 b) {
	return max(0.0, dot(a, b));
}

float positiveDot(vec4 a, vec4 b) {
	return max(0.0, dot(a, b));
}

void setupDirectionLight(int index) {
	lightVector = normalize((u_LightMatrix * -u_LightDirection[index]).xyz);
	lightPower = 1.0;
}

void setupOmniLight(int index) {
	vec3 longLightVector = (u_LightMatrix * (u_LightPosition[index] - v_VertexModelPosition)).xyz;
	lightVector = normalize(longLightVector);
	lightPower = max(0.0, (u_LightDistance[index] - length(longLightVector)) / u_LightDistance[index]);
}

void setupSpotlight(int index) {
	vec3 longLightVector = (u_LightMatrix * (u_LightPosition[index] - v_VertexModelPosition)).xyz;
	lightVector = normalize(longLightVector);
	vec3 lightTargetDirection = normalize((u_LightMatrix * -u_LightDirection[index]).xyz);
	float angleCos = dot(lightVector, lightTargetDirection);
	float angleFactor = max(0.0, (angleCos - u_LightAngleCos[index]) / (1.0 - u_LightAngleCos[index]));
	lightPower = angleFactor * max(0.0, (u_LightDistance[index] - length(longLightVector)) / u_LightDistance[index]);
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
	vec3 camera = normalize(-v_VertexPosition);
	vec3 normal = normalize((u_NormalMatrix * v_VertexNormal).xyz);

	vec3 diffuseLightValue = vec3(0.0, 0.0, 0.0);
	vec3 specularLightValue = vec3(0.0, 0.0, 0.0);

	for (int index = 0; index < u_LightsCount; index++) {
		setupLight(index);
		vec3 halfVector = normalize(vec3(camera + lightVector));
		diffuseLightValue += u_LightColor[index].rgb * positiveDot(normal, lightVector) * lightPower;
		specularLightValue += u_LightColor[index].rgb * pow(positiveDot(halfVector, normal), u_Shininess * (2.0 - lightPower));
	}

	vec4 ambientColor = texture2D(u_AmbientTexture, v_TextureCoordinates);
	vec4 diffuseColor = texture2D(u_DiffuseTexture, v_TextureCoordinates);
	vec4 specularColor = texture2D(u_SpecularTexture, v_TextureCoordinates);

	vec3 ambient = ambientColor.rgb;
	vec3 diffuse = diffuseColor.rgb * diffuseLightValue;
	vec3 specular = specularColor.rgb * specularLightValue;

	gl_FragColor = vec4(specular + diffuse + ambient, diffuseColor.a);
}
