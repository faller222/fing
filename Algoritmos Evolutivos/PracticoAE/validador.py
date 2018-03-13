#!/usr/bin/env python3
# -*- coding: utf-8 -*-
#
#  validador.py
#  
#  Copyright 2017 Santiago Iturriaga <siturria@fing.edu.uy> y Renzo Massobrio <renzom@fing.edu.uy>
#  
#  This program is free software; you can redistribute it and/or modify
#  it under the terms of the GNU General Public License as published by
#  the Free Software Foundation; either version 2 of the License, or
#  (at your option) any later version.
#  
#  This program is distributed in the hope that it will be useful,
#  but WITHOUT ANY WARRANTY; without even the implied warranty of
#  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#  GNU General Public License for more details.
#  
#  You should have received a copy of the GNU General Public License
#  along with this program; if not, write to the Free Software
#  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
#  MA 02110-1301, USA.
#  
#  

import sys

def main(args):
    if len(args)!=3:
        print('Error! Modo de uso: {0} <instancia> <solucion>'.format(args[0]))
        sys.exit(-1)

    path_instancia = args[1]
    path_solucion = args[2]

    num_pedidos = 0
    largo_tabla = 0
    pedidos = {}

    try:
        with open(path_instancia) as instancia:
            num_pedidos = int(instancia.readline())
            largo_tabla = int(instancia.readline())

            for i in range(num_pedidos):
                line = instancia.readline()
                
                largo_pedido = int(line[0:8].strip())
                cantidad_pedido = int(line[9:].strip())

                if largo_pedido in pedidos:
                    pedidos[largo_pedido] += cantidad_pedido
                else:
                    pedidos[largo_pedido] = cantidad_pedido
    except IOError as e:
        print("Error al abrir el archivo '{0}': {1}".format(path_instancia, e.strerror))
        sys.exit(-1)

    cantidad_tablas = 0
    desperdicio = 0

    try:
        with open(path_solucion) as solucion:
            for line in solucion:
                if len(line.strip()) > 0:
                    tabla = [int(x.strip()) for x in line.strip().split(' ')]
                    cantidad_tablas += 1
                    
                    if sum(tabla) > largo_tabla:
                        print('Error! La tabla supera el largo máximo en la línea: {0}'.format(line))
                        sys.exit(-1)

                    desperdicio += largo_tabla - sum(tabla)
            
                    for pieza in tabla:
                        if not pieza in pedidos:
                            print('Error! Una pieza no pertenece a ningún pedido en la línea: {0}'.format(line))
                            sys.exit(-1)
                        else:
                            pedidos[pieza] -= 1
    except IOError as e:
        print("Error al abrir el archivo '{0}': {1}".format(path_solucion, e.strerror))
        sys.exit(-1)

    for k in pedidos:
        if pedidos[k]!=0:
            print('Faltan {0} pedidos de largo {1} en la solución'.format(pedidos[k],k))
            sys.exit(-1)

    print('{0} {1}'.format(cantidad_tablas, desperdicio))
    
    return 0

if __name__ == '__main__':
    sys.exit(main(sys.argv))
