আমি Jetpack Compose এর basic concept এবং মূল অংশগুলো step-by-step সহজভাবে ব্যাখ্যা করি।

1️⃣ Compose কি?

Jetpack Compose হলো Android এর নতুন UI toolkit।
এটি declarative UI approach ব্যবহার করে। অর্থাৎ আপনি বলে দিচ্ছেন কি দেখাতে হবে, না কিভাবে দেখাতে হবে।
পুরনো Android View system এ যেটা করতাম: XML + findViewById + setOnClickListener → Compose এ সেটার প্রয়োজন নেই।

2️⃣ মূল building blocks
2.1 @Composable functions

সব UI component @Composable ফাংশন হিসেবে লেখা হয়।

উদাহরণ:
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name 👋")
}
Text, Button, Image, Column, Row সবই Composable functions।

2.2 Modifiers

Modifier দিয়ে UI size, padding, alignment, color, behavior control করা হয়।
উদাহরণ:
Text(
    text = "Hello",
    modifier = Modifier.fillMaxWidth().padding(16.dp)
)

2.3 Layout Composables

Column → Vertical layout
Row → Horizontal layout
Box → Stack (overlay) layout, Centering, alignment এর জন্য ব্যবহার হয়
Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
    Text("Centered Text")
}

2.4 State

Compose এ UI state-driven।
যদি data change হয়, UI automatically update হয়।
উদাহরণ:
@Composable
fun Counter() {
    var count by remember { mutableStateOf(0) }

    Button(onClick = { count++ }) {
        Text("Count: $count")
    }
}

2.5 Event handling

Button, TextField ইত্যাদির জন্য onClick বা onValueChange lambda ব্যবহার হয়।
Example:
Button(onClick = { /* কিছু করা */ }) {
    Text("Click Me")
}

2.6 Preview

@Preview দিয়ে IDE তে সরাসরি UI দেখতে পারি।
এটি runtime এ দেখাবে না, শুধু IDE preview।
@Preview(showBackground = true)
@Composable
fun PreviewGreeting() {
    Greeting("User")
}

2.7 Navigation

Compose এ Activity বা Screen পরিবর্তন করার জন্য:
পুরনো 방식: Intent(context, SecondActivity::class.java)
নতুন: Navigation Compose library ব্যবহার করা হয়।


Summary:
@Composable function লিখুন → UI define করুন
Modifier দিয়ে layout/style control করুন
State ব্যবহার করে dynamic UI বানান
Button/Click events দিয়ে interaction handle করুন
Preview দিয়ে IDE তে check করুন
setContent { ... } এর মধ্যে main screen attach করুন


👉 সংক্ষেপে
Annotation	কাজ
@Composable	UI function
@Preview	IDE preview
@Stable	Object stable (state কম পরিবর্তন হবে)
@Immutable	Object immutable (data class)
@ReadOnlyComposable	শুধু পড়ার জন্য, কোনো পরিবর্তন করবে না
@OptIn	Experimental API ব্যবহার করার জন্য


