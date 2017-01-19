package bng.dic.esp.sn.bngboussole.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import bng.dic.esp.sn.bngboussole.R;

/**
 * Created by sonyvaio on 18/01/2017.
 */

public class BoussoleView extends View {
    //Rotation vers la droite en degree pour pointer le Nord
    private float northOrientation=0;
    private Paint circlePaint;
    private Paint northPaint;
    private Paint southPaint;
    private Path trianglePath;

    public BoussoleView(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        Resources r = this.getResources();
// Paint pour l'arrière plan de la boussole
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG); // Lisser les formes
        circlePaint.setColor(r.getColor(R.color.compassCircle)); // Définir la couleur
// Paint pour les 2 aiguilles, Nord et Sud
        northPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        northPaint.setColor(r.getColor(R.color.northPointer));
        southPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        southPaint.setColor(r.getColor(R.color.southPointer));
// Path pour dessiner les aiguilles
        trianglePath = new Path();
    }

    public BoussoleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BoussoleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);initView();
    }

    public float getNorthOrientation() {
        return northOrientation;
    }

    public void setNorthOrientation(float northOrientation) {
        if (northOrientation != this.northOrientation)
            this.northOrientation = northOrientation;
        this.invalidate();//on demande à notre vue de se redessiner
    }

    private int measure(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.UNSPECIFIED) {
// Le parent ne nous a pas donné d'indications,
// on fixe donc une taille
            result = 150;
        } else {
// On va prendre la taille de la vue parente
            result = specSize;
        }
        return result;
    }

    // Permet de définir la taille de notre vue
    // /!\ par défaut un cadre de 100x100 si non redéfini
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int d = Math.min(measure(widthMeasureSpec), measure(heightMeasureSpec));
        setMeasuredDimension(d, d);
    }

    protected void onDraw(Canvas canvas) {
        //On détermine le point au centre de notre vue
        int centerX = getMeasuredWidth() / 2;
        int centerY = getMeasuredHeight() / 2;
        int radius = Math.min(centerX, centerY);
// On dessine un cercle avec le " pinceau " circlePaint
        canvas.drawCircle(centerX, centerY, radius, circlePaint);
// On sauvegar
        canvas.save();
        // On tourne le canevas pour que le nord pointe vers le haut
        canvas.rotate(-northOrientation, centerX, centerY);
// on créer une forme triangulaire qui part du centre du cercle et
// pointe vers le haut
        trianglePath.reset();//RAZ du path (une seule instance)
        trianglePath.moveTo(centerX, 10);
        trianglePath.lineTo(centerX - 10, centerY);
        trianglePath.lineTo(centerX + 10, centerY);
// On désigne l'aiguille Nord
        canvas.drawPath(trianglePath, northPaint);
// On tourne notre vue de 180° pour désigner l'auguille Sud
        canvas.rotate(180, centerX, centerY);
        canvas.drawPath(trianglePath, southPaint);
// On restaure la position initiale (inutile dans notre exemple, mais prévoyant)
        canvas.restore();
    }
}
