# PhotoPicker copy rxpicker  
The ImageSelect tool based on **RxJava1**.

[中文文档](./README_CN.md)

## Feature

1. Combined with RxJava , Supper observer pattern to get result
2. Compatible with Android 7.0
3. Custom Image Loader


## Preview

<image src="./image/1.png" width="200px"/> <image src="./image/2.png" width="200px"/> 

## Use Glide

1.Add gradle:

```
compile 'com.github.luhuanxml:PhotoPicker:2.0.0'
```

2.Extends `RxPickerImageLoader` create custom  ImageLoader

```
public class GlideImageLoader implements RxPickerImageLoader {

  @Override public void display(ImageView imageView, String path, int width, int height) {
    Glide.with(imageView.getContext())
        .load(path)
        .error(R.drawable.ic_preview_image)
        .centerCrop()
        .override(width, height)
        .into(imageView);
  }
}
```

3.Initialize RxPicker

```
RxPicker.init(new GlideImageLoader());
```

4.Use

-  Single Image

```
RxPicker.of().start(this).subscribe();
```

- Multiple Images

```
RxPicker.of()
          .single(false)
          .camera(true)
          .limit(3,9)
          .start(this)
          .subscribe();
```
