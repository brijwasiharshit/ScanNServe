import { useState } from "react";
import Button from "../common/Button";
import InputField from "../common/InputField";
import { createCategory } from "../../api/superAdminApi";

export default function CategoryForm({ onCategoryCreated }) {
    const [name, setName] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            await createCategory({ name });
            if (onCategoryCreated) {
                onCategoryCreated();
            }
        } catch (err) {
            setError(err.response?.data?.message || "Failed to create category");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit} className="flex flex-col gap-4">
            {error && (
                <div className="rounded-lg bg-red-50 p-3 text-sm text-red-600">
                    {error}
                </div>
            )}

            <InputField
                label="Category Name"
                placeholder="e.g. Starters"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
            />

            <Button type="submit" className="mt-4" disabled={loading}>
                {loading ? "Creating..." : "Create Category"}
            </Button>
        </form>
    );
}
