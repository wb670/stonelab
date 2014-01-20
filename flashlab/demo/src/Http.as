package
{
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.HTTPStatusEvent;
	import flash.events.ProgressEvent;
	import flash.external.ExternalInterface;
	import flash.net.URLRequest;
	import flash.net.URLStream;
	import flash.system.Security;

	public class Http
	{
		
		private var urlStream:URLStream;
		
		public function Http()
		{
			Security.loadPolicyFile("http://oss.aliyun.test:8080/crossdomain.xml");
			
			urlStream = new URLStream();
			configureListeners(urlStream);
			
			var req:URLRequest = new URLRequest("http://oss.aliyun.test:8080/upload");
			urlStream.load(req);
		}
		
		private function configureListeners(dispatcher:EventDispatcher):void
		{
			dispatcher.addEventListener(Event.COMPLETE, completeHandler);
			dispatcher.addEventListener(Event.OPEN,openHandler);
			dispatcher.addEventListener(HTTPStatusEvent.HTTP_STATUS, statusHandler);
			dispatcher.addEventListener(ProgressEvent.PROGRESS, processHandler);
		}
		
		private function completeHandler(event:Event):void
		{
			call("alert", "complete");
			urlStream.close();
		}
		
		private function openHandler(event:Event):void
		{
			call("alert", "open");
		}
		
		private function statusHandler(event:Event):void
		{
			call("alert", "status");
		}
		
		private function processHandler(event:Event):void
		{
			call("alert", "process");
		}
		
		private function call(func:String, ...params):void
		{
			if(ExternalInterface.available) 
			{
				ExternalInterface.call(func, params);
			}
		}
	}
}