package com.example.siddhantlad.taqneeq;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.siddhantlad.taqneeq.CustomFabAnim.DURATION;
import static java.security.AccessController.getContext;

public class SplashLogo extends AppCompatActivity {
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_logo);
        Window window = SplashLogo.this.getWindow();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                Thread timer= new Thread()
                {
                    public void run()
                    {
                        try
                        {
                            //Display for 0.7 seconds
                            sleep(1000);
                        }
                        catch (InterruptedException e)
                        {
                            // TODO: handle exception
                            e.printStackTrace();
                        }
                        finally
                        {
                            if (user != null) {
                                startActivity(new Intent(SplashLogo.this, MainActivity.class));
                                finish();
                            } else {
                                startActivity(new Intent(SplashLogo.this, LoginActivity.class));
                                finish();
                            }
                        }
                    }
                };
                timer.start();

            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(mAuthListener);
    }
}

/*
package com.example.siddhantlad.taqneeq;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static com.example.siddhantlad.taqneeq.CustomFabAnim.DURATION;
import static java.security.AccessController.getContext;

public class SplashLogo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_logo);
        Window window = SplashLogo.this.getWindow();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final RelativeLayout animlayout=findViewById(R.id.animatinglayout1);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        final ImageView logo=findViewById(R.id.imageView7);
        final ImageView bluebar=findViewById(R.id.imageView10);
        final RelativeLayout relativeLayout=findViewById(R.id.animatinglayout1);
        //RelativeLayout fullLayout=findViewById(R.id.fulllayout);

// clear FLAG_TRANSLUCENT_STATUS flag:

        animlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animlayout.setClickable(false);
                float parentCenterY = relativeLayout.getY() + relativeLayout.getHeight() / 2;
                logo.animate().translationY(-parentCenterY + logo.getHeight() / 2).setDuration(1800);
                bluebar.animate().translationY(parentCenterY - logo.getHeight() / 2 - bluebar.getHeight() - 35).setDuration(1800).setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        bluebar.setBackgroundResource(R.color.BlueCustom);
                        bluebar.animate().scaleX(10f).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(500).setListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                            findViewById(R.id.textView4).setVisibility(View.VISIBLE);
                                findViewById(R.id.textView4).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        startActivity(new Intent(SplashLogo.this,LoginActivity.class));
                                        finish();
                                    }
                                });
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
                animlayout.animate().scaleY(parentCenterY).setInterpolator(new AccelerateDecelerateInterpolator()).setDuration(20000);
            }
        });
        */
/*logo.animate().translationY(-850).setDuration(1800).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        bluebar.animate().translationY(850).setDuration(1800).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
bluebar.setBackgroundResource(R.color.BlueCustom);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });*//*




*/
/*        fab2.animate().translationY(-10).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                actionMenu.close(true);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                img.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
});*//*

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(SplashLogo.this,R.color.Black));

       */
/* Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 0.7 seconds
                    sleep(700);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    //Goes to Activity  StartingPoint.java(STARTINGPOINT)
                    Intent openstartingpoint=new Intent(SplashLogo.this,LoginActivity.class);
                    startActivity(openstartingpoint);
                }
            }
        };
        timer.start();
    }


    //Destroy Welcome_screen.java after it goes to next activity
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        finish();

    *//*
}

    }

*/
