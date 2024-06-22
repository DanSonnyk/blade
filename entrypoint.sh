#!/bin/bash
# Espera até que o banco de dados esteja disponível
wait-for-it.sh mysql:3306 -t 60
