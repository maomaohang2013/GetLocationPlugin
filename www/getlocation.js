function GetLocationPlugin() {
	
}


GetLocationPlugin.prototype.getlocation = function (message,successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "GetLocationPlugin", "getlocation", [message]);
};

GetLocationPlugin.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.GetLocationPlugin = new GetLocationPlugin();
  return window.plugins.GetLocationPlugin;
};

cordova.addConstructor(GetLocationPlugin.install);


