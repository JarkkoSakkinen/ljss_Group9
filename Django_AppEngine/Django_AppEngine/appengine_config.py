import os

on_appengine = os.environ.get('SERVER_SOFTWARE','').startswith('Development')
if on_appengine and os.name == 'nt':
    os.name = None
	
from django.core.wsgi import get_wsgi_application
application = get_wsgi_application()