uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
uniform mat4 u_NormalMatrix;

attribute vec4 a_VertexPosition;
attribute vec4 a_VertexNormal;

varying vec4 v_VertexPosition;
varying vec4 v_VertexNormal;
varying mat4 v_MVMatrix;
varying mat4 v_NormalMatrix;

void main() {
	gl_Position = u_MVPMatrix * a_VertexPosition;
	v_VertexPosition = u_MVMatrix * a_VertexPosition;
	v_VertexNormal = a_VertexNormal;
	v_MVMatrix = u_MVMatrix;
	v_NormalMatrix = u_NormalMatrix;
}
