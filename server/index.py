from flask import *
import base64  
app = Flask(__name__,template_folder='template')
def convert_and_save(b64_string):
    # print(b64_string)
    with open("imageToSave.jpg", "wb") as fh:
        fh.write(base64.b64decode(b64_string.encode()))  
 
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
        return "{'response': 'hello world'}" 
  
if __name__ == '__main__':  
    app.run(host='0.0.0.0')  
