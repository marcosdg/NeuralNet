Marcos Díez García

2-09-2014

# Tabla 4 - Observaciones

  A continuación, señalaremos los puntos clave de los resultados que se han
obtenido para los ficheros de PROBEN1 de la **Tabla 4** (pág. 26), tanto
para la "configuración de control" como las "configuraciones extra" que
podemos encontrar en [configuraciones] (https://github.com/marcosdg/NeuralNet/tree/master/experiments/configs).

  El propósito de distinguir éstos dos conjuntos configuraciones, es 
utilizar las de control como referencia y las extra para ver, sobre uno
de los ficheros de PROBEN1 en concreto, cómo influye alterar los parámetros
de aprendizaje sobre el rendimiento de la red. 

## Configuración de control

### building

  Para las redes *single-layer* (sin capas ocultas) no ha habido una diferencia
significativa entre tener 4, 8 o 16 neuronas en la capa inicial; ni tampoco
ha sido especialmente relevante el si se realizaron 10 o 30 ejecuciones.
Pues para todos los casos los errores en los conjuntos "training", "validation",
y "test" se ha mantenido en promedio por debajo del 5%.

  En cuanto al "sobreajuste", tan sólo en el caso de 30 ejecuciones se ha
observado que para building3.dt el aprendizaje fue menos estable y por ello
se tiene un promedio "bastante" superior al límite establecido en 5% (GL = 5.0).
Para 10 ejecuciones el "sobreajuste" se mantuvo por debajo del 1%.

  Para las redes *multi-layer* (con al menos 1 capa oculta), los resultados han
sido ligeramente mejores si consideramos que el "sobreajuste" se mantuvo en 0%.
Es decir, el aprendizaje fue  más estable que para las redes de 1 sola capa. 

  En general, la red obtuvo un buen rendimiento si tenemos en cuenta que para
todos los casos, en promedio, tan sólo habiendo realizado 1 época se ha
conseguido mantener los errores por debajo del 5%.

### flare


