//
// Created by Mads Lee Jensen on 07/07/16.
// Copyright (c) 2016 Facebook. All rights reserved.
//

#import "RCTImageSequenceView.h"

@implementation RCTImageSequenceView {
    NSUInteger _framesPerSecond;
    NSMutableDictionary *_activeTasks;
    NSMutableDictionary *_imagesLoaded;
    NSMutableArray *_images;
    BOOL _loop;
}

- (void)setImages:(NSArray *)images {
    __weak RCTImageSequenceView *weakSelf = self;

    self.animationImages = nil;

    _activeTasks = [NSMutableDictionary new];
    _imagesLoaded = [NSMutableDictionary new];
    _images = [NSMutableArray new];

    for (NSUInteger index = 0; index < images.count; index++) {
        NSDictionary *item = images[index];

        NSString *url = item[@"uri"];

        dispatch_async(dispatch_queue_create("dk.mads-lee.ImageSequence.Downloader", NULL), ^{
            UIImage *image = [UIImage imageWithData:[NSData dataWithContentsOfURL:[NSURL URLWithString:url]]];

            dispatch_async(dispatch_get_main_queue(), ^{
              [weakSelf onImageLoadTaskAtIndex:index image:image];
            });
        });

        _activeTasks[@(index)] = url;
    }
}

- (void)onImageLoadTaskAtIndex:(NSUInteger)index image:(UIImage *)image {
    if (index == 0) {
        self.image = image;
    }

    [_activeTasks removeObjectForKey:@(index)];

    _imagesLoaded[@(index)] = image;

    if (_activeTasks.allValues.count == 0) {
        [self onImagesLoaded];
    }
}

- (void)onImagesLoaded {
    for (NSUInteger index = 0; index < _imagesLoaded.allValues.count; index++) {
        UIImage *image = _imagesLoaded[@(index)];
        [_images addObject:image];
    }

    [_imagesLoaded removeAllObjects];

    self.animationDuration = _images.count * (1.0f / _framesPerSecond);
    self.animationImages = _images;
    self.animationDuration = _images.count * (1.0f / _framesPerSecond);
    [self performSelector:@selector(animationDidFinish:) withObject:nil afterDelay:self.animationDuration];
}

- (void)setFramesPerSecond:(NSUInteger)framesPerSecond {
    _framesPerSecond = framesPerSecond;

    if (self.animationImages.count > 0) {
        self.animationDuration = self.animationImages.count * (1.0f / _framesPerSecond);
    }
}

- (void)setLoop:(NSUInteger)loop {
    _loop = loop;
    self.animationRepeatCount = _loop ? 0 : 1;

}

- (void)setStart:(NSUInteger)start {
    if(start == 1) {
        [self startAnimating];
    } else {
        [self stopAnimating];
    }
}

- (void)animationDidFinish:(SEL)selector {
  if (self.onAnimationFinish) {
      self.onAnimationFinish(@{});
  }
}


@end
