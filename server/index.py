from flask import *
import base64
import os
from PIL import Image
import numpy as np      
app = Flask(__name__,template_folder='template')
def sigmoid(Z):
    
    A = 1/(1+np.exp(-Z))
    cache = Z
    
    return A, cache

def relu(Z):
    
    A = np.maximum(0,Z)
    
    assert(A.shape == Z.shape)
    
    cache = Z 
    return A, cache

def linear_forward(A, W, b):
    
    Z = W.dot(A) + b
    
    assert(Z.shape == (W.shape[0], A.shape[1]))
    cache = (A, W, b)
    
    return Z, cache
def linear_activation_forward(A_prev, W, b, activation):
    
    
    if activation == "sigmoid":
        # Inputs: "A_prev, W, b". Outputs: "A, activation_cache".
        Z, linear_cache = linear_forward(A_prev, W, b)
        A, activation_cache = sigmoid(Z)
    
    elif activation == "relu":
        # Inputs: "A_prev, W, b". Outputs: "A, activation_cache".
        Z, linear_cache = linear_forward(A_prev, W, b)
        A, activation_cache = relu(Z)
    
    assert (A.shape == (W.shape[0], A_prev.shape[1]))
    cache = (linear_cache, activation_cache)

    return A, cache
def L_model_forward(X, parameters):
    

    caches = []
    A = X
    L = len(parameters) // 2                  # number of layers in the neural network
    
    # Implement [LINEAR -> RELU]*(L-1). Add "cache" to the "caches" list.
    for l in range(1, L):
        A_prev = A 
        A, cache = linear_activation_forward(A_prev, parameters['W' + str(l)], parameters['b' + str(l)], activation = "relu")
        caches.append(cache)
    
    # Implement LINEAR -> SIGMOID. Add "cache" to the "caches" list.
    AL, cache = linear_activation_forward(A, parameters['W' + str(L)], parameters['b' + str(L)], activation = "sigmoid")
    caches.append(cache)
    
    assert(AL.shape == (1,X.shape[1]))
    return AL, caches
def predict(X, y, parameters):
    
    m = X.shape[1]
    n = len(parameters) // 2 # number of layers in the neural network
    p = np.zeros((1,m))
    
    # Forward propagation
    probas, caches = L_model_forward(X, parameters)

    
    # convert probas to 0/1 predictions
    for i in range(0, probas.shape[1]):
        if probas[0,i] > 0.5:
            p[0,i] = 1
        else:
            p[0,i] = 0
            
    #print results
    #print ("predictions: " + str(p))
    #print ("true labels: " + str(y))
    pro=((np.sum((p == y)/m)))
    return (pro,probas[0,i])
def convert_and_save(b64_string):
    # print(b64_string)
    currDir = os.getcwd()
    targetDir = os.path.join(currDir,"server")
    fname = os.path.join(targetDir,"file.jpg")
    with open(fname, "wb") as fh:
        fh.write(base64.b64decode(b64_string.encode()))  
def compute():
    currDir = os.getcwd()
    targetDir = os.path.join(currDir,"server")
    fname = os.path.join(targetDir,"W1.npy")
    W1=np.load(fname)
    fname = os.path.join(targetDir,"W2.npy")
    W2=np.load(fname)
    fname = os.path.join(targetDir,"W3.npy")
    W3=np.load(fname)
    fname = os.path.join(targetDir,"W4.npy")
    W4=np.load(fname)
    fname = os.path.join(targetDir,"b1.npy")
    b1=np.load(fname)
    fname = os.path.join(targetDir,"b2.npy")
    b2=np.load(fname)
    fname = os.path.join(targetDir,"b3.npy")
    b3=np.load(fname)
    fname = os.path.join(targetDir,"b4.npy")
    b4=np.load(fname)
    parameters = {"W1": W1,"b1": b1,"W2": W2,"b2": b2,"W3": W3,"b3": b3,"W4": W4,"b4": b4}
    fname = os.path.join(targetDir,"file.jpg")
    img = Image.open(fname)
    img = img.resize((100,100))
    img = np.array(img)
    x=img.reshape(-1).T
    x.resize(30000,1)
    x=x/255
    print(x)
    pro=predict(x, 1, parameters)
    print(pro)
    return pro
@app.route('/')  
def upload():  
    return render_template("file_upload_form.html")  
 
@app.route('/success', methods = ['POST'])  
def success():  
    if request.method == 'POST':  
        # f = request.files['file']  
        # f.save(f.filename)
        f = request.get_json()
        elem = request.form['image']

        # data = f['image']
        convert_and_save(elem)
        temp = compute()
        midString = "'res':'{}'".format(str(temp[0]))
        endString = "'prob':'{}'".format(str(temp[1]))
        return "{"+midString+","+endString+"}" 
  
if __name__ == '__main__':  
    app.run(host='0.0.0.0')  
