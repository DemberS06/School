
tiempo <- 30:39
ensambles <- c(3,5,6,9,12,25,32,15,9,6)

# Inciso (a)
fx <- ensambles / sum(ensambles)
data.frame(TIEMPO=tiempo, PROBABILIDAD=fx)

# Inciso (b)
plot(x=tiempo, y=fx, pch=19, col="red",
     ylab="Probabilidad", xlab="Tiempo (segs)")

# Inciso (c)
Fx <- cumsum(fx)
data.frame(TIEMPO=tiempo, PROB.ACUM=Fx)

# Inciso (d)
# Fx(37) - Fx(32)
Fx[8] - Fx[3]

# Inciso (e)
# Fx(35)
Fx[6]

# Inciso (f)
# Fx(32.5) = Fx(32)
Fx[3]
