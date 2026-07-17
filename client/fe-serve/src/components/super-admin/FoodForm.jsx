import { useState } from "react";
import Button from "../common/Button";
import InputField from "../common/InputField";
import * as superAdminApi from "../../api/superAdminApi";

export default function FoodForm({ categories, onFoodCreated }) {
    const [name, setName] = useState("");
    const [categoryId, setCategoryId] = useState("");
    const [foodType, setFoodType] = useState("VEG");
    const [defaultImage, setDefaultImage] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleImageUpload = (e) => {
        const file = e.target.files[0];
        if (file) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setDefaultImage(reader.result);
            };
            reader.readAsDataURL(file);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const payload = {
                categoryId: parseInt(categoryId, 10),
                name,
                foodType,
                defaultImage: defaultImage || "https://res.cloudinary.com/demo/image/upload/v1750000000/default-food.jpg"
            };
            const response = await superAdminApi.createGlobalFoodItem(payload);
            onFoodCreated(response.data.data);
        } catch (err) {
            console.error("Failed to create food item:", err);
            setError(err.response?.data?.message || "Failed to create food item");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            {error && <div className="text-red-500 text-sm mb-3">{error}</div>}
            
            <InputField 
                placeholder="Food Name" 
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />
            
            <select
                className="mt-3 w-full rounded-xl border border-slate-200 px-4 py-3 outline-none focus:border-[#F97316] bg-white text-slate-700"
                value={categoryId}
                onChange={(e) => setCategoryId(e.target.value)}
                required
            >
                <option value="" disabled>Select Category</option>
                {categories.map((cat) => (
                    <option key={cat.categoryId} value={cat.categoryId}>
                        {cat.name}
                    </option>
                ))}
            </select>

            <select
                className="mt-3 w-full rounded-xl border border-slate-200 px-4 py-3 outline-none focus:border-[#F97316] bg-white text-slate-700"
                value={foodType}
                onChange={(e) => setFoodType(e.target.value)}
                required
            >
                <option value="VEG">VEG</option>
                <option value="NON_VEG">NON_VEG</option>
            </select>

            <div className="mt-3">
                <label className="block text-sm font-medium text-slate-700 mb-1">Default Image</label>
                <input 
                    type="file" 
                    accept="image/*" 
                    onChange={handleImageUpload}
                    className="w-full text-sm text-slate-500 file:mr-4 file:py-2 file:px-4 file:rounded-xl file:border-0 file:text-sm file:font-semibold file:bg-[#F97316]/10 file:text-[#F97316] hover:file:bg-[#F97316]/20"
                />
            </div>

            <Button type="submit" className="mt-4 w-full" disabled={loading}>
                {loading ? "Saving..." : "Save Item"}
            </Button>
        </form>
    );
}
