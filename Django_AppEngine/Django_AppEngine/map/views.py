from django.http import HttpResponse
from django.template import Context, loader
import json

def index(request):
    template = loader.get_template("map/main_page.html")
    return HttpResponse(template.render())

def ajax(request):
    
    data = [
        51.482238,0.001581,
        51.473364,0.011966,
        51.471974,-0.000651,
        51.472108,-0.002196,
        51.474995,-0.003827,
        51.476492,-0.005629,
        51.477855,-0.006058,
        51.478443,-0.007045,
        51.479298,-0.007861,
        51.481202,-0.002136,
        51.481577,-0.0022
    ]
    json = '{ 51.482238: 0.001581, 51.473364: 0.011966, 51.471974: -0.000651, 51.472108: -0.002196 }'
    
    
    return json
    #return HttpResponse(json, mimetype='application/json')