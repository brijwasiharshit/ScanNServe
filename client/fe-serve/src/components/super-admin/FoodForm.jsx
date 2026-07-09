import Button from "../common/Button";
import InputField from "../common/InputField";

export default function FoodForm() {
    return (
        <form>
            <InputField placeholder="Food Name" />
            <InputField placeholder="Category" className="mt-3" />
            <InputField placeholder="Price" className="mt-3" />

            <textarea
                placeholder="Description"
                className="mt-3 h-24 w-full rounded-xl border border-slate-200 px-4 py-3 outline-none focus:border-[#0891B2]"
            />

            <Button type="submit" className="mt-4 w-full">
                Save Item
            </Button>
        </form>
    );
}
