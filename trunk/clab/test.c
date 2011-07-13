/*
 * lab.c
 *
 *  Created on: 2011-6-10
 *      Author: stone
 */

typedef struct TreeNode *Tree;

struct TreeNode {
	char c;
	Tree left;
	Tree right;
};

Tree newNode(char c) {
	Tree t = malloc(sizeof(Tree));
	t->c = c;
	return t;
}

void list(Tree t) {
	printf("%c", t->c);
	if (t->left) {
		list(t->left);
	}
	if (t->right) {
		list(t->right);
	}
}

void freeTree(Tree t) {
	if (t->left) {
		freeTree(t->left);
	}
	if (t->right) {
		freeTree(t->right);
	}
	free(t);
}

int main() {
	Tree nodes[13];
	//depth 1
	nodes[0] = newNode('+');
	// depth 2
	nodes[1] = newNode('+');
	nodes[2] = newNode('*');
	//depth 3
	nodes[3] = newNode('a');
	nodes[4] = newNode('*');
	nodes[5] = newNode('+');
	nodes[6] = newNode('g');
	//depth 4
	nodes[7] = newNode('b');
	nodes[8] = newNode('c');
	nodes[9] = newNode('*');
	nodes[10] = newNode('f');
	//depth 5
	nodes[11] = newNode('d');
	nodes[12] = newNode('e');

	nodes[0]->left = nodes[1];
	nodes[0]->right = nodes[2];
	nodes[1]->left = nodes[3];
	nodes[1]->right = nodes[4];
	nodes[2]->left = nodes[5];
	nodes[2]->right = nodes[6];
	nodes[4]->left = nodes[7];
	nodes[4]->right = nodes[8];
	nodes[5]->left = nodes[9];
	nodes[5]->right = nodes[10];
	nodes[9]->left = nodes[11];
	nodes[9]->right = nodes[12];

	list(nodes[0]);

	printf("\n");
	int i;
	for (i = 0; i < 12; ++i) {
		free(nodes[i]);
		printf("%i\t%c\n", i, nodes[i]->c);
	}

	return 0;
}
