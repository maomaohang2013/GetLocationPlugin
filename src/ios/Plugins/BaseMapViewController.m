#import "BaseMapViewController.h"
#import "APIKey.h"
#import <MAMapKit/MAMapKit.h>


@interface BaseMapViewController()

@end
@implementation BaseMapViewController

@synthesize mapView = _mapView;
@synthesize search  = _search;



#pragma mark - Utility

- (void)configureAPIKey
{
    
    NSString* File = [[NSBundle mainBundle] pathForResource:@"Info" ofType:@"plist"];
    NSMutableDictionary* dict = [[NSMutableDictionary alloc] initWithContentsOfFile:File];
    
    //NSLog(@"key: %@",[dict objectForKey:@"GAODE_KEY"]);
    APIKey = [dict objectForKey:@"GAODE_KEY"];
    //NSLog(@"key: %@",APIKey);

    if ([APIKey length] == 0)
    {
#define kMALogTitle @"提示"
#define kMALogContent @"apiKey为空，请检查key是否正确设置"
        
        NSString *log = [NSString stringWithFormat:@"[MAMapKit] %@", kMALogContent];
        NSLog(@"%@", log);
        
        dispatch_async(dispatch_get_main_queue(), ^{
            UIAlertView *alert = [[UIAlertView alloc] initWithTitle:kMALogTitle message:kMALogContent delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
            
            [alert show];
        });
    }
    
    [MAMapServices sharedServices].apiKey = (NSString *)APIKey;
}


- (void)clearMapView
{
    self.mapView.showsUserLocation = NO;
    
    [self.mapView removeAnnotations:self.mapView.annotations];
    
    [self.mapView removeOverlays:self.mapView.overlays];
    
    self.mapView.delegate = nil;
}

- (void)clearSearch
{
    self.search.delegate = nil;
}

#pragma mark - Handle Action

- (void)returnAction
{
    
    [self.navigationController popViewControllerAnimated:YES];
    
    [self clearMapView];
    
    [self clearSearch];
    
    self.mapView.userTrackingMode  = MAUserTrackingModeNone;
    
    [self.mapView removeObserver:self forKeyPath:@"showsUserLocation"];
    
    
}

#pragma mark - AMapSearchDelegate

- (void)searchRequest:(id)request didFailWithError:(NSError *)error
{
    NSLog(@"%s: searchRequest = %@, errInfo= %@", __func__, [request class], error);
}

#pragma mark - Initialization

- (void)initMapView
{
 
    self.mapView=[[MAMapView alloc] initWithFrame:CGRectMake(0, 0, 320, 460)];
    self.mapView.delegate = self;
    
    //[self.view addSubview:self.mapView];
    //NSLog(@"initmapview");

}

- (void)initSearch
{
    
    self.search = [[AMapSearchAPI alloc] initWithSearchKey:[MAMapServices sharedServices].apiKey Delegate:nil];
    self.search.delegate = self;
    //NSLog(@"initSearch");
    
}


- (void)mapView:(MAMapView *)mapView didUpdateUserLocation:(MAUserLocation *)userLocation
{
    
    [self searchReGeocode:userLocation.location.coordinate.latitude withLong:userLocation.location.coordinate.longitude];
    self.mapView.showsUserLocation = NO;

}

-(void)mapView:(MAMapView*)mapView didFailToLocateUserWithError:(NSError*)error
{
    NSLog(@"error :%@", error);
}

- (void)searchReGeocode:(CLLocationDegrees) lat withLong:(CLLocationDegrees)longitude
{
    
    AMapReGeocodeSearchRequest *regeoRequest = [[AMapReGeocodeSearchRequest alloc] init];
    regeoRequest.searchType = AMapSearchType_ReGeocode;
    regeoRequest.location = [AMapGeoPoint locationWithLatitude:lat longitude:longitude];
    regeoRequest.radius = 10000;
    regeoRequest.requireExtension = NO;
    [self.search AMapReGoecodeSearch: regeoRequest];

}


- (void)onReGeocodeSearchDone:(AMapReGeocodeSearchRequest *)request response:(AMapReGeocodeSearchResponse *)response
{
    if (response.regeocode != nil)
    {

        NSString *result = response.regeocode.formattedAddress;
        
        NSLog(@"address :%@", result);
        
        NSMutableArray *resultArray = [NSMutableArray arrayWithObjects:result,[NSString stringWithFormat:@"%f", request.location.latitude],[NSString stringWithFormat:@"%f", request.location.longitude], nil];
   
        [[NSNotificationCenter defaultCenter] postNotificationName:@"RELOADVOEWNOTIFICATION" object:resultArray ];
        //[self.view removeFromSuperview];
        [self dismissViewControllerAnimated:NO completion:nil];
               
    }
}

- (void)viewDidAppear:(BOOL)animated
{
    [super viewDidAppear:animated];
    self.mapView.showsUserLocation = YES;
    
}

-(void)viewDidLoad
{
    [super viewDidLoad];
    
    [self configureAPIKey];
    
    [self initMapView];
    
    [self initSearch];
    
}


@end
