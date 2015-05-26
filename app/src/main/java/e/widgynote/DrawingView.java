package e.widgynote;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


public class DrawingView extends View {
    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFF660000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;


    private boolean erase = false;
    private boolean draw = true;
    //private boolean writeText = false;

    public DrawingView(Context context, AttributeSet attrs){
        super(context, attrs);

        //get screen dimensions
        WindowManager wm = (WindowManager) this.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics display = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(display);

        //get actionbar's height - to set size of bitmap
        int actionBarHeight = 0;
        TypedValue tv = new TypedValue();
        if(this.getContext().getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
        }

        //gets rid of black trail that draws before erasing
        //turns off hardware acceleration
        if(Build.VERSION.SDK_INT >= 11){
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        setupDrawing(display.widthPixels, display.heightPixels - actionBarHeight);

    }

//    @Override
//    public void onFinishInflate(){
//        super.onFinishInflate();
//        //Toast.makeText(getContext(), "Inflated :D", Toast.LENGTH_SHORT).show();
//
//    }


    private void setupDrawing(int width, int height){
        canvasBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        drawCanvas = new Canvas(canvasBitmap);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(20);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

//        textbox = new EditText(getContext());
//        textbox.setFocusable(true);
//        textbox.setFocusableInTouchMode(true);
//        textbox.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        textbox.setOnFocusChangeListener(new OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if(!hasFocus){
//                    textbox.setVisibility(View.VISIBLE);
//                    //textbox.setText("");
//                }
//            }
//        });

//        LinearLayout l = (LinearLayout)findViewById(R.id.single_note);
//        l.addView(textbox);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        float touchX = event.getX();
        float touchY = event.getY();

        if(draw) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    drawPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    drawPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    drawCanvas.drawPath(drawPath, drawPaint);
                    drawPath.reset();
                    break;
                default:
                    return false;
            }
        }

        invalidate();
        return true;
    }

    public void setErase(boolean isErase){
        erase = isErase;
        draw = isErase;

        if(erase)
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else
            drawPaint.setXfermode(null);
    }

//    public void setText(boolean isText){
//        writeText = isText;
//    }

    public void setCanvasBitmap(Bitmap b){
        drawCanvas.drawBitmap(b, 0, 0, null);
    }

}
