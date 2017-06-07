if [ "$4" == "" ] ; then echo "HELP run:" 
echo "    ./test.sh SIZE_X MASK_SIZE PARTE CHUNK" 
echo "ej.: ./test.sh 1048576 5 3 256"
exit;fi

SIZE_X=$1
MASK_SIZE=$2
PARTE=$3
CHUNK=$4

echo "Ingresa con valores:"
echo "    SIZE_X: "$SIZE_X
echo "    MASK_SIZE: "$MASK_SIZE
echo "    PARTE: "$PARTE
echo "    CHUNK: "$CHUNK

#define CHUNK 1024
#define SIZE_X 1048576
#define MASK_SIZE 21
#define PARTE 7

if [ ! -d "Tmp" ];then echo "Creando Directorio Tmp"    
  mkdir Tmp;fi

rm  Tmp/*;
cp util.h Tmp/util.h
cp util.cpp Tmp/util.cpp
 
  

sed -e "s/^#define PARTE /#define PARTE ${PARTE} \/\/ /g" -e "s/^#define CHUNK /#define CHUNK ${CHUNK} \/\/ /g" -e "s/^#define SIZE_X /#define SIZE_X ${SIZE_X} \/\/ /g" -e "s/^#define MASK_SIZE /#define MASK_SIZE ${MASK_SIZE} \/\/ /g" convolucion.cu > Tmp/convolucion.cu

ejec=Tmp/C$PARTE-$CHUNK-$SIZE_X-$MASK_SIZE
nvcc Tmp/convolucion.cu -o $ejec util.cpp -m32 -arch=compute_20 -code=sm_20
resultado=Tmp/R$PARTE-$CHUNK-$SIZE_X-$MASK_SIZE.txt
./$ejec
