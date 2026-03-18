import json
import time

from coloreado_grafo import realizar_voraz

def cargar_grafo(ruta):
    with open(ruta) as f:
        datos = json.load(f)
    return datos["grafo"]

if __name__ == "__main__":
    tam = [8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536]
    rep = 50

    for n in tam:
        try:
            ruta = f"sols/g{n}.json"
            grafo = cargar_grafo(ruta)
            t1=time.time()
            for i in range(rep):
                realizar_voraz(grafo)
            t2 = time.time()
            tiempo=(t2-t1)*1000
            print(f"n={n} **TIEMPO={int(tiempo)}**nVeces={rep}")
        except Exception as e:
            print(f"Error con n={n}")
            print(e)

