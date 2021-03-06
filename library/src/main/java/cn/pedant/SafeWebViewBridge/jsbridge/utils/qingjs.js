(function() {

  if (window.harbor) {
    // Android加上了这个if判断，如果当前window已经定义了returnTheData对象，不再重新加载
    // 避免重新初始化_callback_map等变量，导致之前的消息回调失败
    //alert('window already has a harbor object!!!');
    return;
  };

  ///////////////////////////////////////////////////////////////////////////////////////////////// 
  ///////////////////////////////////本地调用的实际逻辑////////////////////////////////////////////
  var _CUSTOM_PROTOCOL_SCHEME = 'harbors',
    callbacksCount = 1,
    callbacks = {};

  function _returnTheData(callbackId, message) {
	 
    try {
      var callback = callbacks[callbackId];
      if (!callback) return;
      callback.apply(null, [message]);
    } catch (e) {
      alert(e)
    }
  }

  // Use this in javascript to request native objective-c code
  // functionName : string (I think the name is explicit :p)
  // args : array of arguments
  // callback : function with n-arguments that is going to be called when the native code returned
  function _call(functionName, message, callback) {

    var hasCallback = callback && typeof callback == "function";
    var callbackId = hasCallback ? callbacksCount++ : 0;

    if (hasCallback)
      callbacks[callbackId] = callback;

    var iframe = document.createElement("IFRAME");
    iframe.setAttribute("src", _CUSTOM_PROTOCOL_SCHEME + ":" + functionName + ":" + callbackId + ":" + encodeURIComponent(JSON.stringify(message)));
    // For some reason we need to set a non-empty size for the iOS6 simulator...
    iframe.setAttribute("height", "1px");
    iframe.setAttribute("width", "1px");
    document.documentElement.appendChild(iframe);
    iframe.parentNode.removeChild(iframe);
    iframe = null;

  }




  var __harbor = {
    // public
    invoke: _call,
    call: _call,
    returnTheData: _returnTheData
  };

  window.harbor = __harbor;

})();