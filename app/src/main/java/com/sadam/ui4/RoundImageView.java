//package com.sadam.ui4;
//
///**
// * 圆角图片控件
// */
//public class RoundImageView extends ImageView {
//
//    public RoundImageView(Context context) {
//        super(context);
//        init();
//    }
//
//    public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
//        super(context, attrs, defStyle);
//        init();
//    }
//
//    public RoundImageView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    private final RectF roundRect = new RectF();
//    private float rect_adius = 10;
//    private final Paint maskPaint = new Paint();
//    private final Paint zonePaint = new Paint();
//    private void init() {
//        maskPaint.setAntiAlias(true);
//        maskPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        //
//        zonePaint.setAntiAlias(true);
//        zonePaint.setColor(Color.WHITE);
//        //
//        float density = getResources().getDisplayMetrics().density;
//        rect_adius = rect_adius * density;
//    }
//
//    public void setRectAdius(float adius) {
//        rect_adius = adius;
//        invalidate();
//    }
//
//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right,
//                            int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        int w = getWidth();
//        int h = getHeight();
//        roundRect.set(0, 0, w, h);
//    }
//
//    @Override
//    public void draw(Canvas canvas) {
//        try {
//            canvas.saveLayer(roundRect, zonePaint, Canvas.ALL_SAVE_FLAG);
//            canvas.drawRoundRect(roundRect, rect_adius, rect_adius, zonePaint);
//            //
//            canvas.saveLayer(roundRect, maskPaint, Canvas.ALL_SAVE_FLAG);
//            super.draw(canvas);
//            canvas.restore();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//}