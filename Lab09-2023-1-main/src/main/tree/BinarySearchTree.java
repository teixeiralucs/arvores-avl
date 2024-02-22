package tree;

import estrut.Tree;

public class BinarySearchTree implements Tree {
    private TreeNode root;

    @Override
    public boolean buscaElemento(int valor) {
        return buscaElemento(root, valor);
    }

    private boolean buscaElemento(TreeNode node, int valor) {
        if (node == null) {
            return false;
        }
        if (node.valor == valor) {
            return true;
        } else if (valor < node.valor) {
            return buscaElemento(node.esquerda, valor);
        } else {
            return buscaElemento(node.direita, valor);
        }
    }

    @Override
    public int minimo() {
        if (root == null) {
            throw new IllegalStateException("Árvore vazia.");
        }
        return minimo(root);
    }

    private int minimo(TreeNode node) {
        if (node.esquerda == null) {
            return node.valor;
        }
        return minimo(node.esquerda);
    }

    @Override
    public int maximo() {
        if (root == null) {
            throw new IllegalStateException("Árvore vazia.");
        }
        return maximo(root);
    }

    private int maximo(TreeNode node) {
        if (node.direita == null) {
            return node.valor;
        }
        return maximo(node.direita);
    }

    public void insereElemento(int valor) {
        root = insereElemento(root, valor);
    }
    
    private TreeNode insereElemento(TreeNode node, int valor) {
        if (node == null) {
            return new TreeNode(valor);
        }
    
        if (valor < node.valor) {
            node.esquerda = insereElemento(node.esquerda, valor);
        } else if (valor > node.valor) {
            node.direita = insereElemento(node.direita, valor);
        }
    
        // Calcula o fator de equilíbrio do nó
        int balance = calcularFatorEquilibrio(node);
    
        // Verifica se o nó se tornou desbalanceado
        // Rotações são necessárias para balancear a árvore
        // Caso Esquerda-Esquerda
        if (balance > 1 && valor < node.esquerda.valor) {
            return rotacaoDireita(node);
        }
        // Caso Direita-Direita
        if (balance < -1 && valor > node.direita.valor) {
            return rotacaoEsquerda(node);
        }
        // Caso Esquerda-Direita
        if (balance > 1 && valor > node.esquerda.valor) {
            node.esquerda = rotacaoEsquerda(node.esquerda);
            return rotacaoDireita(node);
        }
        // Caso Direita-Esquerda
        if (balance < -1 && valor < node.direita.valor) {
            node.direita = rotacaoDireita(node.direita);
            return rotacaoEsquerda(node);
        }
    
        return node;
    }
    
    private TreeNode rotacaoDireita(TreeNode y) {
        TreeNode x = y.esquerda;
        TreeNode T2 = x.direita;
    
        x.direita = y;
        y.esquerda = T2;
    
        return x;
    }
    
    private TreeNode rotacaoEsquerda(TreeNode x) {
        TreeNode y = x.direita;
        TreeNode T2 = y.esquerda;
    
        y.esquerda = x;
        x.direita = T2;
    
        return y;
    }
    
    private int calcularAltura(TreeNode node) {
        if (node == null)
            return 0;
        return Math.max(calcularAltura(node.esquerda), calcularAltura(node.direita)) + 1;
    }
    
    private int calcularFatorEquilibrio(TreeNode node) {
        if (node == null)
            return 0;
        return calcularAltura(node.esquerda) - calcularAltura(node.direita);
    }
    

    @Override
    public void remove(int valor) {
        root = remove(root, valor);
    }

    private TreeNode remove(TreeNode node, int valor) {
        if (node == null) {
            return null;
        }
        if (valor < node.valor) {
            node.esquerda = remove(node.esquerda, valor);
        } else if (valor > node.valor) {
            node.direita = remove(node.direita, valor);
        } else {
            if (node.esquerda == null) {
                return node.direita;
            } else if (node.direita == null) {
                return node.esquerda;
            }
            node.valor = minimo(node.direita);
            node.direita = remove(node.direita, node.valor);
        }
        return node;
    }

    @Override
    public int[] preOrdem() {
        int[] result = new int[tamanho()];
        preOrdem(root, result, 0);
        return result;
    }

    private int preOrdem(TreeNode node, int[] array, int index) {
        if (node == null) {
            return index;
        }
        array[index++] = node.valor;
        index = preOrdem(node.esquerda, array, index);
        index = preOrdem(node.direita, array, index);
        return index;
    }

    @Override
    public int[] emOrdem() {
        int[] result = new int[tamanho()];
        emOrdem(root, result, 0);
        return result;
    }

    private int emOrdem(TreeNode node, int[] array, int index) {
        if (node == null) {
            return index;
        }
        index = emOrdem(node.esquerda, array, index);
        array[index++] = node.valor;
        index = emOrdem(node.direita, array, index);
        return index;
    }

    @Override
    public int[] posOrdem() {
        int[] result = new int[tamanho()];
        posOrdem(root, result, 0);
        return result;
    }

    private int posOrdem(TreeNode node, int[] array, int index) {
        if (node == null) {
            return index;
        }
        index = posOrdem(node.esquerda, array, index);
        index = posOrdem(node.direita, array, index);
        array[index++] = node.valor;
        return index;
    }

    private int tamanho() {
        return tamanho(root);
    }

    private int tamanho(TreeNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + tamanho(node.esquerda) + tamanho(node.direita);
    }

    private static class TreeNode {
        int valor;
        TreeNode esquerda;
        TreeNode direita;

        TreeNode(int valor) {
            this.valor = valor;
        }
    }
}
