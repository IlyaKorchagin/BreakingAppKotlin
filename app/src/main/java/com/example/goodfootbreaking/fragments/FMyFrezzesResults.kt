package com.example.goodfootbreaking.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.goodfootbreaking.util.CustomProgressBar
import android.graphics.Typeface
import android.content.Intent
import android.view.View
import com.example.goodfootbreaking.activities.DescriptionActivity
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.goodfootbreaking.R
import com.example.goodfootbreaking.databinding.FragmentFmyFrezzesResultsBinding
import com.example.goodfootbreaking.model.Pupils

class FMyFrezzesResults  /*
    // Масштабирование картинок
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Начальная высота и ширина изображения
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Рассчитываем наибольшее значение inSampleSize, которое равно степени двойки
            // и сохраняем высоту и ширину, когда они больше необходимых
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // Сначала вызываем декодер с опцией inJustDecodeBounds=true для проверки разрешения
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Подсчитываем inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Теперь вызываем декодер с установленной опцией inSampleSize
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
     */
    : Fragment(), View.OnClickListener {
    private var _binding: FragmentFmyFrezzesResultsBinding? = null
    private val binding get() = _binding!!

    var pageNumber = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageNumber = arguments!!.getInt(ARGUMENT_PAGE_NUMBER)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFmyFrezzesResultsBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.handler = this

        binding.babyProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageBabyButton.setOnClickListener(this)

        binding.turtleProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageTurtel.setOnClickListener(this)

        binding.chairProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageChair.setOnClickListener(this)

        binding.shoulderProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageShoulder!!.setOnClickListener(this)

        binding.elbowProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageElbow.setOnClickListener(this)

        binding.headProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHead.setOnClickListener(this)

        binding.headHollowbackProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHeadHollowback.setOnClickListener(this)

        binding.hollowbackProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageHollowback.setOnClickListener(this)

        binding.oneHandProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageOnehand.setOnClickListener(this)

        binding.invertProgressBar.setStateDescriptionData(CustomProgressBar.descriptionData)
        binding.imageInvert.setOnClickListener(this)

        binding.babyTitle.text = "Baby - " + pupil!!.babyfrezze.toString() + "%"
        CustomProgressBar.setProgress(pupil!!.babyfrezze, binding.babyProgressBar)
        if (pupil!!.subscription > 0) {
            binding.shoulderTitle.text = "Shoulder - " + pupil!!.shoulderfrezze.toString() + "%"
            CustomProgressBar.setProgress(pupil!!.shoulderfrezze, binding.shoulderProgressBar)
        } else {
            binding.imageShoulder.setImageResource(R.drawable.locked)
            binding.shoulderProgressBar.visibility = View.INVISIBLE
            binding.shoulderTitle.textSize = 14f
            binding.shoulderTitle.setTypeface(null, Typeface.BOLD)
            binding.shoulderTitle.text = "Shoulder - Подписка отсутствует"
        }
        if (pupil!!.subscription > 0) {
            binding.turtelTitle!!.text =
                "Turtle - " + pupil!!.turtlefrezze.toString() + "%"
            CustomProgressBar.setProgress(pupil!!.turtlefrezze, binding.turtleProgressBar)
        } else {
            binding.imageTurtel.setImageResource(R.drawable.locked)
            binding.turtleProgressBar.visibility = View.INVISIBLE
            binding.turtelTitle.textSize = 12f
            binding.turtelTitle.text = "Turtle - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            binding.headTitle.text = "Head - " + pupil!!.headfrezze.toString() + "%"
            CustomProgressBar.setProgress(pupil!!.headfrezze, binding.headProgressBar)
        } else {
            binding.imageHead.setImageResource(R.drawable.locked)
            binding.headProgressBar.visibility = View.INVISIBLE
            binding.headTitle.textSize = 12f
            binding.headTitle.text = "Head - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.babyfrezze >= 20) and (pupil!!.turtlefrezze >= 20)) {
                binding.chairTitle.text = "Chair - " + pupil!!.chairfrezze.toString() + "%"
                CustomProgressBar.setProgress(pupil!!.chairfrezze, binding.chairProgressBar)
            } else {
                binding.imageChair.setImageResource(R.drawable.locked)
                binding.chairProgressBar.visibility = View.INVISIBLE
                binding.chairTitle.textSize = 12f
                binding.chairTitle.text = "Chair\nТребуется Baby > 20 и Turtle > 20"
            }
        } else {
            binding.imageChair.setImageResource(R.drawable.locked)
            binding.chairProgressBar.visibility = View.INVISIBLE
            binding.chairTitle.textSize = 12f
            binding.chairTitle.text = "Chair - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.babyfrezze >= 50) and (pupil!!.turtlefrezze >= 40)) {
                binding.elbowTitle.text = "Elbow - " + pupil!!.elbowfrezze.toString() + "%"
                CustomProgressBar.setProgress(pupil!!.elbowfrezze, binding.elbowProgressBar)
            } else {
                binding.imageElbow.setImageResource(R.drawable.locked)
                binding.elbowProgressBar.visibility = View.INVISIBLE
                binding.elbowTitle.textSize = 12f
                binding.elbowTitle.text = "Elbow\nТребуется Baby > 50 и Turtle > 40"
            }
        } else {
            binding.imageElbow.setImageResource(R.drawable.locked)
            binding.elbowProgressBar.visibility = View.INVISIBLE
            binding.elbowTitle.textSize = 12f
            binding.elbowTitle.text = "Elbow - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.headfrezze >= 70) {
                binding.headHollowbackTitle.text =
                    "HeadHollowback - " + pupil!!.headhollowbackfrezze.toString() + "%"
                CustomProgressBar.setProgress(
                    pupil!!.headhollowbackfrezze,
                    binding.headHollowbackProgressBar
                )
            } else {
                binding.imageHeadHollowback.setImageResource(R.drawable.locked)
                binding.headHollowbackProgressBar.visibility = View.INVISIBLE
                binding.headHollowbackTitle.textSize = 12f
                binding.headHollowbackTitle.text = "HeadHollowback\nТребуется Head > 70"
            }
        } else {
            binding.imageHeadHollowback.setImageResource(R.drawable.locked)
            binding.headHollowbackProgressBar.visibility = View.INVISIBLE
            binding.headHollowbackTitle.textSize = 12f
            binding.headHollowbackTitle.text = "HeadHollowback - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if ((pupil!!.handstand >= 50) and (pupil!!.bridge >= 90) and (pupil!!.headhollowbackfrezze >= 60)) {
                binding.hollowbackTitle.text =
                    "Hollowback - " + pupil!!.hollowbackfrezze.toString() + "%"
                CustomProgressBar.setProgress(pupil!!.hollowbackfrezze, binding.hollowbackProgressBar)
            } else {
                binding.imageHollowback.setImageResource(R.drawable.locked)
                binding.hollowbackProgressBar.visibility = View.INVISIBLE
                binding.hollowbackTitle.textSize = 12f
                binding.hollowbackTitle.text =
                    "Hollowback\nТребуется Cтойка на руках > 50 и Мостик > 90 и Headhollowback >60"
            }
        } else {
            binding.imageHollowback.setImageResource(R.drawable.locked)
            binding.hollowbackProgressBar.visibility = View.INVISIBLE
            binding.hollowbackTitle.textSize = 12f
            binding.hollowbackTitle.text = "Hollowback - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.handstand >= 50) {
                binding.oneHandTitle.text =
                    "One Hand - " + pupil!!.onehandfrezze.toString() + "%"
                CustomProgressBar.setProgress(pupil!!.onehandfrezze, binding.oneHandProgressBar)
            } else {
                binding.imageOnehand.setImageResource(R.drawable.locked)
                binding.oneHandProgressBar.visibility = View.INVISIBLE
                binding.oneHandTitle.textSize = 12f
                binding.oneHandTitle.text = "One Hand\nТребуется Стойка на руках > 50"
            }
        } else {
            binding.imageOnehand.setImageResource(R.drawable.locked)
            binding.oneHandProgressBar.visibility = View.INVISIBLE
            binding.oneHandTitle.textSize = 12f
            binding.oneHandTitle.text = "One Hand - Заблокировано"
        }
        if (pupil!!.subscription > 0) {
            if (pupil!!.handstand >= 40) {
                binding.invertTitle.text =
                    "Invert - " + pupil!!.invertfrezze.toString() + "%"
                CustomProgressBar.setProgress(pupil!!.invertfrezze, binding.invertProgressBar)
            } else {
                binding.imageInvert.setImageResource(R.drawable.locked)
                binding.invertProgressBar.visibility = View.INVISIBLE
                binding.invertTitle.textSize = 12f
                binding.invertTitle.text = "Invert\nТребуется Стойка на руках > 40"
            }
        } else {
            binding.imageInvert.setImageResource(R.drawable.locked)
            binding.invertProgressBar.visibility = View.INVISIBLE
            binding.invertTitle.textSize = 12f
            binding.invertTitle.text = "Invert - Заблокировано"
        }
        return view
    }

    // Переход на страницу описания элемента
    override fun onClick(v: View) {
        val intent:Intent
        if (pupil!!.subscription > 0) {
            when (v.id) {
                R.id.imageBabyButton -> {
                     intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Baby Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.babyfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageTurtel -> {
                    val intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Turtel Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.turtlefrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageShoulder -> {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Shoulder Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.shoulderfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageHead -> {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Head Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.headfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageChair -> if ((pupil!!.babyfrezze >= 20) and (pupil!!.turtlefrezze >= 20)) {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Chair Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.chairfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageHeadHollowback -> if (pupil!!.headfrezze >= 90) {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "HeadHollowback Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_RATING,
                        pupil!!.headhollowbackfrezze
                    )
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageOnehand -> if (pupil!!.handstand >= 50) {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Onehand Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.onehandfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageInvert -> if (pupil!!.handstand >= 40) {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Invert Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.invertfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageHollowback -> if ((pupil!!.handstand >= 50) and (pupil!!.bridge >= 90) and (pupil!!.headhollowbackfrezze >= 60)) {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Hollowback Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_RATING,
                        pupil!!.hollowbackfrezze
                    )
                    startActivity(intent) // создаем интент для связи активностью
                }
                R.id.imageElbow -> if ((pupil!!.babyfrezze >= 50) and (pupil!!.turtlefrezze >= 40)) {
                    intent = Intent(
                        activity,
                        DescriptionActivity::class.java
                    ) // создаем интент для связи активностью для редактирования БД
                    intent.putExtra(
                        DescriptionActivity.DESCRIPTION_MESSAGE,
                        "Elbow Frezze"
                    ) // передаем параметр "название элемента"
                    intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.elbowfrezze)
                    startActivity(intent) // создаем интент для связи активностью
                }
            }
        } else {
            if (v.id == R.id.imageBabyButton) {
                val intent = Intent(
                    activity,
                    DescriptionActivity::class.java
                ) // создаем интент для связи активностью для редактирования БД
                intent.putExtra(
                    DescriptionActivity.DESCRIPTION_MESSAGE,
                    "Baby Frezze"
                ) // передаем параметр "название элемента"
                intent.putExtra(DescriptionActivity.DESCRIPTION_RATING, pupil!!.babyfrezze)
                startActivity(intent) // создаем интент для связи активностью
            } else {
                Toast.makeText(
                    context,
                    "Для полноценной работы приложения нужна подписка",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    companion object {
        private var pupil: Pupils? = null
        const val ARGUMENT_PAGE_NUMBER = "arg_page_number"
        @JvmStatic
        fun newInstance(page: Int, curPupil: Pupils?): FMyFrezzesResults {
            val pageFragment = FMyFrezzesResults()
            pupil = curPupil
            val arguments = Bundle()
            arguments.putInt(ARGUMENT_PAGE_NUMBER, page)
            pageFragment.arguments = arguments
            return pageFragment
        }
    }
}