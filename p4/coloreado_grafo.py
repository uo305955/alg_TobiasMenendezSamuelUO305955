import json

from auxiliar import dibujar_mapa_coloreado, generar_mapa_grafo

def realizar_voraz(grafo):
    colores=["red","blue","green","yellow","orange","purple","cyan","magenta","lime"]
    solucion={}

    for nodo in grafo:
        for color in colores:
            disponible=True
            for vecino in grafo[nodo]:
                vecino = str(vecino)
                if vecino in solucion and solucion[vecino] == color:
                    disponible = False
                    break
            if disponible:
                solucion[nodo] = color
                break
    return solucion


if __name__ == "__main__":
    n = 4
    mapa = generar_mapa_grafo(n)
    solucion = realizar_voraz(mapa["grafo"])

    if solucion:
        print("Solución encontrada:", solucion)
        dibujar_mapa_coloreado(mapa, solucion)
        with open('sols/solucion.json', 'w') as f:
            json.dump(solucion, f)
            f.close()
    else:
        print("No se encontró solución.")
