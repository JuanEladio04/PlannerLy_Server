#!/bin/bash

echo "--- Iniciando script de conexión Docker ---"

# 1. Verifica si docker-compose está instalado
echo "Verificando si 'docker-compose' está instalado..."
if ! command -v docker-compose &> /dev/null; then
    echo "ERROR: 'docker-compose' no está instalado. Por favor, instálalo para continuar."
    exit 1
fi
echo "'docker-compose' encontrado."

# 2. Obtén el nombre del proyecto
PROYECTO="Plannerly_Server"
echo "Nombre del proyecto configurado: '$PROYECTO'"

# 3. Obtiene el ID del contenedor correspondiente al proyecto
echo "Buscando el ID del contenedor para el proyecto '$PROYECTO'..."
CONTENEDOR_ID_RAW=$(docker ps -qf "name=$PROYECTO")
# echo "Comando ejecutado: docker ps -qf \"name=$PROYECTO\"" # Descomenta para ver la salida cruda del comando
# echo "Salida obtenida (ID crudo): '$CONTENEDOR_ID_RAW'" # Descomenta para ver la salida cruda del comando

CONTENEDOR_ID=$(echo "$CONTENEDOR_ID_RAW" | head -n 1) # Asegura que solo tomamos el primer ID

if [ -z "$CONTENEDOR_ID" ]; then
    echo "ERROR: No se pudo obtener un ID de contenedor en ejecución para el proyecto '$PROYECTO'."
    echo "Asegúrate de que el contenedor esté corriendo. Puedes usar 'docker ps' para verificarlo."
    exit 1
fi
echo "ID del contenedor encontrado: '$CONTENEDOR_ID'"

# 4. Verifica si el contenedor está en ejecución
echo "Verificando el estado del contenedor '$CONTENEDOR_ID'..."
if docker ps -q -f id="$CONTENEDOR_ID" -f status=running &> /dev/null; then
    echo "El contenedor '$PROYECTO' (ID: $CONTENEDOR_ID) está en ejecución."
    echo "Abriendo una shell interactiva dentro del contenedor..."

    # --- ¡CAMBIO CLAVE AQUÍ! ---
    # Usamos docker exec -it para abrir una shell (bash o sh)
    docker exec -it "$CONTENEDOR_ID" bash || docker exec -it "$CONTENEDOR_ID" sh

    echo "Sesión interactiva finalizada. Volviendo al shell anfitrión."
else
    echo "ERROR: El contenedor '$PROYECTO' (ID: $CONTENEDOR_ID) no está en ejecución o no existe."
    echo "Por favor, inicia el contenedor usando 'docker start $CONTENEDOR_ID' o 'docker-compose up'."
    exit 1
fi

echo "--- Script de conexión Docker finalizado ---"
