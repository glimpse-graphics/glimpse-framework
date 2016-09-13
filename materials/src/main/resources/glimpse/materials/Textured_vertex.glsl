uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
uniform mat4 u_LightMatrix;
uniform mat4 u_NormalMatrix;

attribute vec4 a_VertexPosition;
attribute vec4 a_VertexNormal;
attribute vec2 a_TextureCoordinates;

varying vec3 v_VertexPosition;
varying vec4 v_VertexNormal;
varying vec2 v_TextureCoordinates;

varying mat4 v_LightMatrix;
varying mat4 v_NormalMatrix;

void main() {
	gl_Position = u_MVPMatrix * a_VertexPosition;
	v_VertexPosition = (u_MVMatrix * a_VertexPosition).xyz;
	v_VertexNormal = a_VertexNormal;
	v_LightMatrix = u_LightMatrix;
	v_NormalMatrix = u_NormalMatrix;
	v_TextureCoordinates = a_TextureCoordinates;
}
