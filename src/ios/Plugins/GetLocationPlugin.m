#import "GetLocationPlugin.h"
#import "BaseMapViewController.h"


static NSString *message,*callbackID,*address,*latitude,*longitude;

@implementation GetLocationPlugin

- (void)getlocation:(CDVInvokedUrlCommand*)command {
   
    message  = [command.arguments objectAtIndex:0];
    
    callbackID = command.callbackId;
    
    [self sendNotification];
  }



- (void)reloadView: (NSNotification *)sender
{
    NSMutableArray *array = sender.object;
    address = [array objectAtIndex:0];
    latitude = [array objectAtIndex:1];
    longitude = [array objectAtIndex:2];
    [self.webView.window.rootViewController dismissModalViewControllerAnimated:YES];
    
    if ([message isEqualToString:@"address"]) {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:address];
        [self writeJavascript:[pluginResult toSuccessCallbackString:callbackID]];
    } else if([message isEqualToString:@"longitude"]) {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:longitude];
        [self writeJavascript:[pluginResult toSuccessCallbackString:callbackID]];
    } else if([message isEqualToString:@"latitude"]) {
        CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:latitude];
        [self writeJavascript:[pluginResult toSuccessCallbackString:callbackID]];
    }

    
}

- (void)sendNotification
{
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(reloadView:) name:@"RELOADVOEWNOTIFICATION" object:nil];
    BaseMapViewController *baseMapViewController = [[BaseMapViewController alloc] init];
    baseMapViewController.view.backgroundColor = [UIColor clearColor];
       self.webView.window.rootViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
    [self.webView.window.rootViewController presentModalViewController:baseMapViewController animated:YES];
}




@end