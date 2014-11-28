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
    
    if ([message isEqualToString:@"address"]) {
        [self.commandDelegate runInBackground:^{
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:address];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackID];
        }];
        
        
    } else if([message isEqualToString:@"latitude"]) {
        [self.commandDelegate runInBackground:^{
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:latitude];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackID];
        }];
        
        
    } else if([message isEqualToString:@"longitude"]) {
        [self.commandDelegate runInBackground:^{
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:longitude];
            [self.commandDelegate sendPluginResult:pluginResult callbackId:callbackID];
        }];
        
    }
}

- (void)sendNotification
{
  
    [[NSNotificationCenter defaultCenter] addObserver:self selector:@selector(reloadView:) name:@"RELOADVOEWNOTIFICATION" object:nil];
    BaseMapViewController *baseMapViewController = [[BaseMapViewController alloc] init];
    baseMapViewController.view.backgroundColor = [UIColor clearColor];
    
    if ([[[UIDevice currentDevice] systemVersion] floatValue] > 8.0) {
        baseMapViewController.modalPresentationStyle = UIModalPresentationOverCurrentContext;
        NSLog([[UIDevice currentDevice] systemVersion]);
    } else {
        NSLog([[UIDevice currentDevice] systemVersion]);
        self.webView.window.rootViewController.modalPresentationStyle = UIModalPresentationCurrentContext;
    }
    [self.webView.window.rootViewController presentViewController:baseMapViewController animated:NO completion:nil];
   

}




@end