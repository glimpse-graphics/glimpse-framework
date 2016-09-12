uniform sampler2D u_DiffuseTexture;
uniform sampler2D u_AmbientTexture;
uniform sampler2D u_SpecularTexture;

uniform float u_Shininess;

uniform vec4 u_LightDirection;

varying vec3 v_VertexPosition;
varying vec4 v_VertexNormal;
varying vec2 v_TextureCoordinates;

varying mat4 v_LightMatrix;
varying mat4 v_NormalMatrix;

float positiveDot(vec3 a, vec3 b) {
	return max(0.0, dot(a, b));
}

float positiveDot(vec4 a, vec4 b) {
	return max(0.0, dot(a, b));
}

void main() {
	vec3 camera = normalize(-v_VertexPosition);
	vec3 normal = normalize((v_NormalMatrix * v_VertexNormal).xyz);
	vec3 light = normalize((v_LightMatrix * -u_LightDirection).xyz);
	vec3 halfVector = normalize(vec3(camera + light));

	float diffuseValue = positiveDot(normal, light);
	float specularValue = pow(positiveDot(halfVector, normal), u_Shininess);

	vec4 ambientColor = texture2D(u_AmbientTexture, v_TextureCoordinates);
	vec4 diffuseColor = texture2D(u_DiffuseTexture, v_TextureCoordinates);
	vec4 specularColor = texture2D(u_SpecularTexture, v_TextureCoordinates);

	vec3 ambient = ambientColor.rgb;
	vec3 diffuse = diffuseColor.rgb * diffuseValue;
	vec3 specular = specularColor.rgb * specularValue;

	gl_FragColor = vec4(specular + diffuse + ambient, diffuseColor.a);
}
