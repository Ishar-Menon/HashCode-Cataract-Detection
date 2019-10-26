import numpy as np
import cv2

eye_cascade = cv2.CascadeClassifier(cv2.data.haarcascades+'haarcascade_eye.xml')
img = cv2.imread('eyes3.jpg')
eyes = eye_cascade.detectMultiScale(img,1.05,5)
n=0
for (ex,ey,ew,eh) in eyes:
    cv2.rectangle(img,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)
    word="crop"+str(n)+".jpg"
    cv2.imwrite(word,img[ey:ey+eh,ex:ex+ew])
    n=n+1

cv2.imshow('img',img)
