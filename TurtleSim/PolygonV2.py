#!/usr/bin/env python
#Programa por Victor Romero (S1GL3R) from RedCircleDev /// Dibujo de Poligonos Automatizados para Tutlesim de ROS
#Pequeño programa para ROS TurtleSim, que permite a la tortuga dibujar cualquier polígono que tenga todos sus lados de la misma longitud

#Lo único a tener en cuenta es que necesitas Ubuntu y ROS instalado para ejecutar el programa, crea o pega el Polygon.py en un espacio de trabajo, una vez allí, 
#inicia Roscore, arranca TurtleSim y ejecuta el Polygon.py, Polygon.py trabaja con dos variables, la primera es el número de lados que tendrá el polígono, 
#la otra es la longitud de esos lados.

import rospy
from geometry_msgs.msg import Twist
from turtlesim.msg import Pose
import sys
import math

#variables globales para el manejo del robot

robot_x = 0 #posicion actual x
robot_y = 0 #posicion actual y
c = 0 #contador de lados
plusminus = 0 #bandera para ubicar cuadrante que seguimos (x+y+ / x-y+ / x-y- / x+y-)
angcont = 0 #contador de angulos acumulados (sirve para ubicar en que cuadrante trabajamos) /// bandera para saber si la posicion final debe ser mayor o menor a x y y
rAngulo = 0 #angulo relativo para el giro
rate = 0 #taza a a la que trabaja el turtlesim
pos_ix = 0 #posicion inicial x
pos_iy = 0 #posicion inical y
pos_fx = 0 #posicion final x
pos_fy = 0 #posicion final y

#funcion para actualizar posicion actual de robot
def pose_callback(pose):
	global robot_x
	#rospy.loginfo("Robot X = %f\n",pose.x)
	robot_x = pose.x

	global robot_y
	#rospy.loginfo("Robot Y = %f\n",pose.y)
	robot_y = pose.y
#funcion para realizar el giro del robot
def giro (robot_x,robot_y,vel,pub,angulo,lenght,sides):
	#definicion de variables globales
	global rAngulo	
	global c
    	global rate	
	global plusminus 
    	global angcont
    	global pos_ix
    	global pos_iy
    	global pos_fx
    	global pos_fy	
	#contador + 1 porque ya terminamos un lado
	c = c+1
	#detenemos tortuga
	vel.linear.x = 0
	#proceso para girar
	vel.angular.z = abs(1)
	t0 = rospy.Time.now().to_sec()
	#angulo inicial
	anguloA = 0
	#inicia giro (el giro se calcula relativamente, puede mejorar)
	while(anguloA < rAngulo):
		pub.publish(vel)
		t1 = rospy.Time.now().to_sec()
		anguloA = 1*(t1-t0)
	#detenemos giro
	vel.angular.z = 0
	pub.publish(vel)
	rate.sleep()
	#calculo de cuadrante actual 0-90 x+y+ 90-180 x-y+ 180-270 x-y- 270 360  +y-
	angcont = angcont + angulo
	if angcont < 90: #x+y+
		#calculo de siguientes posiciones
		pos_ix = robot_x
 		pos_iy = robot_y
		#calculo de catetos
		auxang = math.radians(angcont) #transformamos grados a radianes
   		aux1 =  (math.cos(auxang)*lenght) #cateto adyacente
   		aux2 =  (math.sin(auxang)*lenght) #cateto opuesto
		#comprobar negativos
    		if aux1 < 0:
			aux1 = aux1 * -1
    		if aux2 < 0:
			aux2 = aux2 * -1
		pos_fx = pos_ix + aux1
		pos_fy = pos_iy + aux2
	#estas funciones solo suman o restan la cara a la posicion actual, ya que son lineas rectas paralelas al eje x o y
	elif angcont == 90: #arriba 
		#calculo de siguientes posiciones
		pos_ix = robot_x
    		pos_iy = robot_y
		pos_fx = pos_ix + 1
		pos_fy = pos_iy + (lenght)
	elif angcont == 180:  #izquierda
		#calculo de siguientes posiciones
		pos_ix = robot_x
    		pos_iy = robot_y
		pos_fx = pos_ix - (lenght)
		pos_fy = (pos_iy) - 1
	elif angcont == 270: #abajo
		#calculo de siguientes posiciones
		pos_ix = robot_x
    		pos_iy = robot_y
		pos_fx = (pos_ix ) - 1
		pos_fy = pos_iy - (lenght)
	#--------------------------#
	elif angcont >90 and angcont <= 180: #x-y+
		#calculo de siguientes posiciones
		pos_ix = robot_x
    		pos_iy = robot_y
		#calculo de catetos
		auxang = math.radians(angcont - 90) #transformamos grados a radianes (-90 por el cuadrante)
   		aux1 =  (math.cos(auxang)*lenght) #cateto adyacente
   		aux2 =  (math.sin(auxang)*lenght) #cateto opuesto
		#comprobar negativos
    		if aux1 < 0:
			aux1 = aux1 * -1
    		if aux2 < 0:
			aux2 = aux2 * -1
		pos_fx = pos_ix - aux2
		pos_fy = pos_iy + aux1
	elif angcont >180 and angcont <= 270: #x-y-
		#calculo de siguientes posiciones
		pos_ix = robot_x
    		pos_iy = robot_y
		#calculo de catetos
		auxang = math.radians(angcont - 180) #transformamos grados a radianes (-180 por el cuadrante)
   		aux1 =  (math.cos(auxang)*lenght) #cateto adyacente
   		aux2 =  (math.sin(auxang)*lenght) #cateto opuesto
		#comprobar negativos
    		if aux1 < 0:
			aux1 = aux1 * -1
   		if aux2 < 0:
			aux2 = aux2 * -1
		pos_fx = pos_ix - aux1
		pos_fy = pos_iy - aux2
	elif angcont >270 and angcont <= 360: #x+y-
		#calculo de siguientes posiciones
		pos_ix = robot_x
    		pos_iy = robot_y
		#calculo de catetos
		auxang = math.radians(angcont - 270) #transformamos grados a radianes (-270 por el cuadrante)
   		aux1 =  (math.cos(auxang)*lenght) #cateto adyacente
   		aux2 =  (math.sin(auxang)*lenght) #cateto opuesto
		#comprobar negativos
    		if aux1 < 0:
			aux1 = aux1 * -1
   		if aux2 < 0:
			aux2 = aux2 * -1
		pos_fx = pos_ix + aux2
		pos_fy = pos_iy - aux1
	
	#imprimimos las posiciones calculadas
    	rospy.loginfo("Robot IX = %f\n",pos_ix)
    	rospy.loginfo("Robot IY = %f\n",pos_iy)
    	rospy.loginfo("Robot FX = %f\n",pos_fx)
    	rospy.loginfo("Robot FY = %f\n",pos_fy)
	#comprobamos bandera plusminus
	#caso especial linea recta paralela a x o y
	if angcont == 90 or angcont == 180 or angcont == 270:
		if angcont == 90:
			plusminus = 0
		else:
			plusminus = 1
	elif robot_x < pos_fx and robot_y < pos_fy: # x+ y+
		plusminus = 0
		#tolerancia a errores
		pos_fx = pos_fx - 0
		pos_fy = pos_fy - 0
	elif robot_x > pos_fx and robot_y > pos_fy: # x- y-
		plusminus = 1
		#tolerancia a errores
		pos_fx = pos_fx + (0)
		pos_fy = pos_fy + (0)
	elif  robot_x > pos_fx and robot_y < pos_fy: # x- y+
		plusminus = 2
		#tolerancia a errores
		pos_fx = pos_fx + (0)
		pos_fy = pos_fy - 0
	elif  robot_x < pos_fx and robot_y > pos_fy: # x+ y-
		plusminus = 3
		#tolerancia a errores
		pos_fx = pos_fx - 0
		pos_fy = pos_fy + 0
	elif  robot_x < pos_fx and robot_y == pos_fy: # x+ y=
		plusminus = 0
	elif  robot_x > pos_fx and robot_y == pos_fy: # x- y=
		plusminus = 1 
	elif  robot_x == pos_fx and robot_y < pos_fy: # x= y+
		plusminus = 0  
	elif  robot_x == pos_fx and robot_y > pos_fy: # x= y-
		plusminus = 1    
	else:
		plusminus = 999 #error
	#imprimimos informacion adicional
	rospy.loginfo("plusminus = %f\n", plusminus)
	rospy.loginfo("angulo total= %f\n",angcont)
	rospy.loginfo("llllllllllllllllll")

	#inicia caminata normal

	vel.linear.x = 1
#funcion que inicia el proceso
def move_turtle(sides, lenght):
    global robot_x
    global robot_y
    rospy.init_node('mover', anonymous=False)
    pub = rospy.Publisher('/turtle1/cmd_vel', Twist, queue_size=10)
    rospy.Subscriber('/turtle1/pose',Pose, pose_callback)
    #obtener angulo externo
    angulo = 360/sides
    
    pi = 3.14159265 #pi para calculo de vuelta relativa
    global rAngulo
    rAngulo = angulo*2*pi/360 #angulo relativo para parar las vueltas
    global c
    global rate
    rate = rospy.Rate(10) # 10hz
    global plusminus
    global angcont 
    global pos_ix
    global pos_iy
    global pos_fx
    global pos_fy
    vel = Twist()
    rate.sleep()
    #entramos a ciclo
    while not rospy.is_shutdown():
	#calculo e impresion de primeros valores
	pos_ix = robot_x
    	pos_iy = robot_y
    	pos_fx = pos_ix + (lenght)
    	pos_fy = pos_iy + 1

    	rospy.loginfo("Robot IX = %f\n",pos_ix)
    	rospy.loginfo("Robot IY = %f\n",pos_iy)
    	rospy.loginfo("Robot FX = %f\n",pos_fx)
    	rospy.loginfo("Robot FY = %f\n",pos_fy)
	rospy.loginfo("llllllllllllllllll")
	#ciclo infinito hasta terminar los lados
	while(c < sides):
		#avanza
		vel.linear.x = 1
		vel.linear.y = 0
		vel.linear.z = 0

		vel.angular.x = 0
		vel.angular.y = 0
		vel.angular.z = 0
		#x+ y+ / x+ y= / x= y+
		if (robot_x >= pos_fx or robot_y >= pos_fy) and plusminus == 0:
			giro (robot_x,robot_y,vel,pub,angulo,lenght,sides)
		#x- y- / x- y= / x= y-
		elif (robot_x <= pos_fx or robot_y <= pos_fy) and plusminus == 1:
			giro (robot_x,robot_y,vel,pub,angulo,lenght,sides)
		#x- y+
		elif (robot_x <= pos_fx or robot_y >= pos_fy) and plusminus == 2:
			giro (robot_x,robot_y,vel,pub,angulo,lenght,sides)
		#x+ y-
		elif (robot_x >= pos_fx or robot_y <= pos_fy) and plusminus == 3:
			giro (robot_x,robot_y,vel,pub,angulo,lenght,sides)
		pub.publish(vel)
		rate.sleep()
	#si terminamos la figura salimos del programa
	if c==sides:
		break 

if __name__ == '__main__':
    try:
        move_turtle(float(sys.argv[1]),float(sys.argv[2]))
    except rospy.ROSInterruptException:
        pass
