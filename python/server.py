from flask import Flask, request, send_file, jsonify
from PIL import Image
from io import BytesIO
import redis
import json
app = Flask(__name__)

@app.route('/')
def hello_world():
    return 'Hello!'

@app.route('/image', methods=['POST'])
def image():
    data = BytesIO(request.get_data())
    image = Image.open(data)
    image = image.resize((200, 200), Image.BICUBIC)
    output = BytesIO()
    image.save(output, 'jpeg', quality=80)
    output.seek(0)
    print('done')
    return send_file(output, mimetype='image/jpeg')

client = redis.StrictRedis(host='localhost')
@app.route('/redis', methods=['POST'])
def redis():
    client.set('pythonkey', json.dumps(request.json))
    raw = client.get('pythonkey')
    print('done')
    response = json.loads(raw)
    return jsonify(response)


if __name__ == '__main__':
    app.run(port=4000)
