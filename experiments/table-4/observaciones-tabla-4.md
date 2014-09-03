Marcos Díez García

3-09-2014

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

Para las redes **single-layer** (sin capas ocultas) no ha habido una diferencia
significativa entre tener 4, 8 o 16 neuronas en la capa inicial; ni tampoco
ha sido especialmente relevante el si se realizaron 10 o 30 ejecuciones.
Pues para todos los casos los errores en los conjuntos "training", "validation",
y "test" se ha mantenido en promedio por debajo del 5%.

En cuanto al "sobreajuste", tan sólo en el caso de 30 ejecuciones se ha
observado que para building3.dt el aprendizaje fue menos estable y por ello
se tiene un promedio "bastante" superior al límite establecido en 5% (GL = 5.0).
Para 10 ejecuciones el "sobreajuste" se mantuvo por debajo del 1%.

Para las redes **multi-layer** (con al menos 1 capa oculta), los resultados han
sido ligeramente mejores si consideramos que el "sobreajuste" se mantuvo en 0%.
Es decir, el aprendizaje fue  más estable que para las redes de 1 sola capa. 

En general, la red obtuvo  **muy buen rendimiento** si tenemos en cuenta que para
todos los casos, en promedio, se ha conseguido mantener los errores por debajo del 5%.

### flare

En este ejemplo, lo principal a destacar es que tanto para multi-layer o single-layer
con 10 o 30 ejecuciones, en **todos los casos el aprendizaje ha sido más inestable** que
el ejemplo de building. Para ello, se ha observado un incremento significativo en la
desviación estándar del sobreajuste, lo cuál es un indicativo de que durante la 
época el ajuste de los pesos fue relativamente brusco.

Aunque también cabe señalar que, para el caso de **multi-layer, ha sido algo mejor** que
para single-layer. Pues el promedio en el sobreajuste está alrededor de 15%, mientras
que en single-layer está más cerca del 25%.

A pesar de tal inestabilidad, los errores en los conjuntos de "training", "validation" y
"test" fueron realmente bajos, en promedio ninguno superó el 3%. Podemos decir, entonces,
que el rendimiento ha sido **bastante bueno** aunque el proceso de aprendizaje fue algo 
más inestable.

### hearta

En éste caso, se ha observado un **notable empeoramiento del rendimiento**,  respecto el
error en todos los conjuntos de datos; obteniendo, en promedio, errores entorno al **30%**.
En single-layer se puede observar que empleando tanto 8 como 16 neuronas en la capa 
inicial consigue reducir el error ligeramente, en comparación con utilizar 4 neuronas.
Respecto al sobreajuste, no ha habido inestabilidad exceptuando la red de 16 nodos 
evaluada sobre hearta3.dt.

Sorprendentemente, para multi-layer hemos observado que todas las arquitecturas se han
comportado de manera similar al caso de 4 neuronas en single-layer. Es decir, que para
éste problema las redes de 1 capa con **8 o 16 neuronas** han sido las **mejores** en 
cuanto a mantener errores bajos. Sin embargo, en ningún caso de multi-layer ha habido 
inestabilidad.

Cabe señalar, que el autor de PROBEN1 nos indica que éste problema no tiene muchos datos
de entrenamiento y que hay valores desconocidos para algunas de las muestras. Ésta es 
probablemente la causa del empeoramiento del rendimiento que comentábamos. Por tanto,
lo que hemos aprendido es que **tan importante es la arquitectura de la red como los datos
que utilizamos para entrenarla y evaluarla**.

### heartac

Ésta es una de las diferentes versiones del conjunto de datos de "heart", y para la cúal
hemos obtenido unos resultados similares a los del caso anterior, "hearta".

En primer lugar, destacar que aquí también hubo un empeoramiento en el rendimiento, en lo
que respecta a los **errores** en los conjuntos de datos (training, valiation y test). 
Pero, han sido ligeramente menores en comparación con "hearta", entorno al **20%**.

En segundo lugar, hemos obtenido que el **sobreajuste** durante el proceso de aprendizaje
ha sido mayor para single-layer, e incluso también se ha notado en algunos casos de
multi-layer. Probablemente, debido a que en general la **inestabilidad se incrementó** en
todos los casos, en comparación con "hearta".

De manera similar a "hearta", también se aprecia que para single-layer han sido
ligeramente mejores las redes de 8 y 16 neuronas. Y para multi-layer no ha habido
una diferencia significativa en tener 2, 4 u 8 neuronas en alguna de las capas de la red.

En definitiva, las redes han disminuido ligeramente los errores pero el aprendizaje fue
más inestable.

### Conclusión

Como resumen de lo que se ha observado en los benchmark, destacamos los siguientes puntos:

* En las arquitecturas multicapa el tener 2, 4 u 8 neuronas en alguna de las capas
no ha repercutido significativamente en las estadísticas.

* Las arquitecturas multicapa consiguieron reducir el sobreajuste más que las monocapa

* Si hay un número reducido de datos o algunos se desconocen, como el caso del conjunto
de datos "heart", podemos tener problemas durante el entrenamiento de la red. Es 
precisamente la falta de datos lo que hace el aprendizaje no-supervisado más "difícil"
que el supervisado.

* En los problemas con mayor numero de muestras hemos observado que las estadísticas 
han sido más homogéneas entre las distintas particiones (xxx1.dt, xxx2.dt, xxx3.dt),
 para 10 y 30 runs.

* En algunos casos, como "hearta", han funcionado mejor las redes monocapa con 8 o 16 
neuronas. Y en casi todos los casos las multicapa han reducido ligeramente los errores.

El motivo de por qué han funcionado mejor las multicapa puede deberse al hecho de que
tanto en problemas de clasificación como aproximación, si nuestra red tiene que adoptar
un comportamiento no-lineal, necesitaremos introducir más capas con el objetivo de
hacer una correcta separación de los datos en categorías (clasificación) o conseguir una 
función mejor ajustada a la deseada (aproximación).
