//
//  AppDelegate.m
//  SortingAlgorithms
//
//  Created by Logan on 12/5/12.
//  Copyright (c) 2012 Logan. All rights reserved.
//

#import "AppDelegate.h"

@implementation AppDelegate

- (void)dealloc
{
    [super dealloc];
}

- (void)applicationDidFinishLaunching:(NSNotification *)aNotification
{
    // Insert code here to initialize your application
    NSMutableArray *items = [[NSMutableArray alloc] init];
    [items addObject:[NSNumber numberWithInt:6135]];
    [items addObject:[NSNumber numberWithInt:9123]];
    [items addObject:[NSNumber numberWithInt:1404]];
    [items addObject:[NSNumber numberWithInt:2973]];
    [items addObject:[NSNumber numberWithInt:1862]];
    
    NSLog(@"%@", items);
    
    items = [self quickSort:items];
    
    NSLog(@"%@", items);
}

- (NSMutableArray *) radixSort: (NSMutableArray *) items
{
    int shift = 1;
    int max = [[items objectAtIndex:0] intValue];
    for (int i = 1; i < items.count; i++)
        if ([[items objectAtIndex:i] intValue] > max)
            max = [[items objectAtIndex:i] intValue];
    int toPlace = ceil((log10(max)));
    
    NSMutableArray *buckets = [[NSMutableArray alloc] init];
    for (int i = 0; i < 10; i++)
        [buckets addObject:[[NSMutableArray alloc] init]];
    
    for (int i = 0; i <= toPlace; i++)
     {
        for (int j = 0; j < items.count; j++)
         {
            int bucketNumber = ([[items objectAtIndex:j] intValue] / shift) % 10;
            [[buckets objectAtIndex:bucketNumber] addObject:[items objectAtIndex:j]];
         }
        NSMutableArray *combineBuckets = [[NSMutableArray alloc] init];
        for (int k = 0; k < [buckets count]; k++)
         {
            [combineBuckets addObjectsFromArray:[buckets objectAtIndex:k]];
            [[buckets objectAtIndex:k] removeAllObjects];
         }

        items = [NSMutableArray arrayWithArray:combineBuckets];
        shift *= 10;
     }
    return items;
}

-(NSMutableArray *) mergeSort: (NSMutableArray *) items
{
    return [self mergeSort:items with:0 and:((int)items.count - 1)];
}

- (NSMutableArray *) mergeSort: (NSMutableArray *) items with: (int) low and:(int) high
{
    if (low < high)
     {
        int middle = low + (high - low) / 2;
        [self mergeSort:items with:low and:middle];
        [self mergeSort:items with:middle+1 and:high];
        [self merge:low and:middle and:high in:items];
     }
    return items;
}
-(void) merge: (int) low and:(int) middle and: (int) high in: (NSMutableArray *) items
{
    NSMutableArray *helper = [[NSMutableArray alloc] initWithArray:items];
    int i = low;
    int j = middle + 1;
    int k = low;
    while (i <= middle && j <= high)
     {
        if ([[helper objectAtIndex:i] intValue]  <= [[helper objectAtIndex:j] intValue])
         {
            [items replaceObjectAtIndex:k withObject:[helper objectAtIndex:i]];
            i++;
         }
        else
         {
            [items replaceObjectAtIndex:k withObject:[helper objectAtIndex:j]];
            j++;
         }
        k++;
     }
    while (i <= middle)
     {
        [items replaceObjectAtIndex:k withObject:[helper objectAtIndex:i]];
        k++;
        i++;
     }
}

-(NSMutableArray *) quickSort: (NSMutableArray *) items
{
    return [self quickSort:items with:0 and:((int) items.count - 1)];
}
-(NSMutableArray *) quickSort: (NSMutableArray *) items with:(int) low and:(int) high
{
    int i = low;
    int j = high;
    int pivot = [[items objectAtIndex:(low + (high - low) / 2)] intValue];
    
    while (i <= j)
     {
        while ([[items objectAtIndex:i] intValue] < pivot)
            i++;
        
        while ([[items objectAtIndex:j] intValue] > pivot)
            j--;
        
        if (i <= j)
         {
            int temp = [[items objectAtIndex:i] intValue];
            [items replaceObjectAtIndex:i withObject:[items objectAtIndex:j]];
            [items replaceObjectAtIndex:j withObject:[NSNumber numberWithInt:temp]];
            i++;
            j--;
         }
     }
    if (low < j)
        [self quickSort:items with:low and:j];
    if (i < high)
        [self quickSort:items with:i and:high];
    return items;
}

@end
