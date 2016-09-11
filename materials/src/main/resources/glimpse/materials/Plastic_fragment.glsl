uniform vec4 u_DiffuseColor;
uniform vec4 u_AmbientColor;
uniform vec4 u_SpecularColor;

uniform float u_Shininess;

uniform vec4 u_LightDirection;
uniform vec4 u_CameraPosition;

varying vec4 v_VertexPosition;
varying vec4 v_VertexNormal;
varying mat4 v_MVMatrix;
varying mat4 v_NormalMatrix;

float positiveDot(vec4 a, vec4 b) {
	return max(0.0, dot(a, b));
}

void main() {
	vec4 camera = normalize(u_CameraPosition - v_VertexPosition);
	vec4 normal = vec4(normalize((v_NormalMatrix * v_VertexNormal).xyz), 1.0);
	vec4 reflectNormal = normalize(v_NormalMatrix * v_VertexNormal);
	vec4 light = normalize(-u_LightDirection);
	vec4 reflectedLight = reflect(light, reflectNormal);

	float diffuseValue = positiveDot(v_VertexNormal, light);
	float specularValue = pow(positiveDot(reflectedLight, camera), u_Shininess) * 2.0;

	vec3 ambient = u_AmbientColor.rgb * 0.2;
	vec3 diffuse = u_DiffuseColor.rgb * 0.8 * diffuseValue;
	vec3 specular = u_SpecularColor.rgb * specularValue;

	gl_FragColor = vec4(specular + diffuse + ambient, u_DiffuseColor.a);
}
