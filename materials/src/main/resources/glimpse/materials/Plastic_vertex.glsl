uniform mat4 u_MVPMatrix;
uniform mat4 u_MVMatrix;
uniform mat4 u_ModelMatrix;

attribute vec4 a_VertexPosition;
attribute vec4 a_VertexNormal;

varying vec3 v_VertexPosition;
varying vec4 v_VertexModelPosition;
varying vec4 v_VertexNormal;

void main() {
	gl_Position = u_MVPMatrix * a_VertexPosition;

	v_VertexPosition = (u_MVMatrix * a_VertexPosition).xyz;
	v_VertexModelPosition = (u_ModelMatrix * a_VertexPosition);
	v_VertexNormal = a_VertexNormal;
}
