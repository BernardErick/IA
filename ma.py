import queue
import random
class Node:
    
    id = -1
    pai = None
    custo = 0
    h = 0
    peso = 0
    def __init__(self,id):
        self.id = id
    def __lt__(self, other):
        return self.custo < other.custo          
    def _str_(self):
        return "Node(" + str(self.id) + ")"
class Grafo:
    
    matriz = []
    n = 0
    direcionado = False
    
    def __init__(self,n,direcionado):
        self.n = n
        self.direcionado = direcionado
        for i in range(n):
            self.matriz.append([0]*n)            
    
    def addAresta(self,s,t,value):
        if(not self.direcionado):
            self.matriz[t][s]=value
        self.matriz[s][t]=value
        
    def printMatriz(self):
        print()
        print('##########')
        for i in range(self.n):
            for j in range(self.n):
                print(self.matriz[i][j],end = ' ')
            print()
        print('##########')
        print()
    
    def bl(self,s,t):
        q = queue.Queue()
        
        node = Node(s)
        node.pai = Node(-1)       
        
        q.put(node)
        
        while(not q.empty()):
            aux = q.get()
            
            # Teste de Objetivo           
            if(aux.id == t):
                return aux
            # Teste de Objetivo
            
            # Expansão de vizinhos            
            for i in range(self.n):                
                if(self.matriz[aux.id][i] != 0 and i != aux.pai.id):
                    node = Node(i)
                    node.pai = aux
                    q.put(node)
            # Expansão de vizinhos
        
        return aux
    
    def bp(self,s,t):
        pilha = []

        node = Node(s)
        node.pai = Node(-1)

        pilha.insert(0,node)

        while(len(pilha) != 0):
            aux = pilha.pop()
            # Teste de Objetivo           
            if(aux.id == t):
                return aux
            # Teste de Objetivo
            
            # Expansão de vizinhos            
            for i in range(self.n):                
                if(self.matriz[aux.id][i] != 0 and i != aux.pai.id):
                    node = Node(i)
                    node.pai = aux
                    pilha.insert(0,node)
            # Expansão de vizinhos     
        return aux
    
    def bcu(self,s,t):
        pq = queue.PriorityQueue()
        
        node = Node(s)
        node.pai = Node(-1)
        node.custo = 0

        pq.put((node.custo, node))

        while not pq.empty():
            # Tirando o nó com menor custo da fila de prioridade
            # pattern matching
            _, nodeAux = pq.get()
            
            # Teste de Objetivo
            if nodeAux.id == t:
                return nodeAux
            # Teste de Objetivo
            
            # Expansão de vizinhos
            for i in range(self.n):
                # Verifica se o nó i é vizinho do nó atual e não sendo o pai
                if (self.matriz[nodeAux.id][i] != 0 and i != nodeAux.pai.id):
                    # Criando um nó vizinho e jogando na fila com seu peso
                    node = Node(i)
                    node.pai = nodeAux
                    node.custo = nodeAux.custo + self.matriz[nodeAux.id][i]
                    
                    pq.put((node.custo, node))
            # Expansão de vizinhos           
        return nodeAux

    def bme(self,s,t,h):
        pq = queue.PriorityQueue()
        
        node = Node(s)
        node.pai = Node(-1)
        node.custo = 0

        pq.put((node.custo, node))

        while not pq.empty():
            # Tirando o nó com menor custo da fila de prioridade
            # pattern matching
            _, nodeAux = pq.get()
            
            # Teste de Objetivo
            if nodeAux.id == t:
                return nodeAux
            # Teste de Objetivo
            
            # Expansão de vizinhos
            for i in range(self.n):
                # Verifica se o nó i é vizinho do nó atual e não sendo o pai
                if (self.matriz[nodeAux.id][i] != 0 and i != nodeAux.pai.id):
                    # Criando um nó vizinho e jogando na fila com seu peso
                    node = Node(i)
                    node.pai = nodeAux
                    node.custo = h[i]
                
                    pq.put((node.custo, node))
            # Expansão de vizinhos           
        return nodeAux
    
    def bastar(self,s,t,h):
        pq = queue.PriorityQueue()
        
        node = Node(s)
        node.pai = Node(-1)
        node.custo = 0

        pq.put((node.custo, node))

        while not pq.empty():
            # Tirando o nó com menor custo da fila de prioridade
            # pattern matching
            _, nodeAux = pq.get()
            
            # Teste de Objetivo
            if nodeAux.id == t:
                return nodeAux
            # Teste de Objetivo
            
            # Expansão de vizinhos
            for i in range(self.n):
                # Verifica se o nó i é vizinho do nó atual e não sendo o pai
                if (self.matriz[nodeAux.id][i] != 0 and i != nodeAux.pai.id):
                    # Criando um nó vizinho e jogando na fila com seu peso
                    node = Node(i)
                    node.pai = nodeAux
                    node.custo = h[i] + nodeAux.custo + self.matriz[nodeAux.id][i]
                
                    pq.put((node.custo, node))
            # Expansão de vizinhos           
        return nodeAux    
def heuristica_gen(n, target):
    random.seed()
    print("-=-=-=- GERANDO HEURISTICA RANDOM -=-=-=-")
    arr = [n]
    for i in range(n):
        if i is not target:
            arr.append(random.randrange(1,10))
        else:
            arr[target] = 0
        print(str(i)+": "+str(arr[i]))
    return arr

# Sibiu - 0
# Fagaras - 1
# Rimnicu Vilceu - 2
# Pitesti - 3
# Bucharest - 4


g = Grafo(5,False)
g.printMatriz()

g.addAresta(0, 1, 80)
g.addAresta(0, 2, 99)
g.addAresta(1, 3, 97)
g.addAresta(3, 4, 101)
g.addAresta(2, 4, 211)

g.printMatriz()

objetivo = g.bl(0, 4)

print("-=-=-=-=-=-BUSCA EM LARGURA-=-=-=-=-")   
while(objetivo.id != -1):
    print(objetivo.id)
    objetivo = objetivo.pai

print("-=-=-=-=-=-BUSCA EM PROFUNDIDADE-=-=-=--=--")

buscaEmBp = g.bp(0,4)
while(buscaEmBp.id != -1):
    print(buscaEmBp.id)
    buscaEmBp = buscaEmBp.pai

buscamEmBcu = g.bcu(0, 4)
print("-=-=-=-=-=-BUSCA DE CUSTO UNIFORME-=-=-=-=-")   
while(buscamEmBcu.id != -1):
    print(buscamEmBcu.id)
    buscamEmBcu = buscamEmBcu.pai

h = heuristica_gen(5,4)

buscaEmBme = g.bme(0,4,h)
print("-=-=-=-=-=-BUSCA DE MELHOR ESCOLHA-=-=-=-=-")   
while(buscaEmBme.id != -1):
    print(buscaEmBme.id)
    buscaEmBme = buscaEmBme.pai

buscaEmAStar = g.bastar(0,4,h)
print("-=-=-=-=-=-BUSCA DE A* -=-=-=-=-")   
while(buscaEmAStar.id != -1):
    print(buscaEmAStar.id)
    buscaEmAStar = buscaEmAStar.paiv
