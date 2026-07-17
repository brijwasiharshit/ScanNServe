import { Edit, Trash2 } from "lucide-react";
import Button from "../common/Button";

export default function MenuList({ menu, onEdit, onRemove }) {
    if (!menu || menu.length === 0) {
        return <div className="mt-6 text-slate-500">No menu items found. Please subscribe to some food items.</div>;
    }

    return (
        <div className="mt-6 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
            {menu.map((item) => (
                <div
                    key={item.itemId}
                    className="rounded-2xl border border-[#E2E8F0] bg-white p-5 flex flex-col shadow-sm"
                >
                    <div className="flex-1">
                        <h3 className="text-lg font-semibold">{item.itemName || item.name}</h3>
                        <p className="text-sm text-slate-500 mb-2">{item.categoryName || item.category}</p>
                        <p className="text-sm text-slate-600 mb-4">{item.description}</p>
                        <p className="text-2xl font-bold text-[#155E75]">Rs. {item.price}</p>
                        <span className={`inline-block mt-2 px-2 py-1 text-xs font-semibold rounded-full ${item.available ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>
                            {item.available ? 'Available' : 'Unavailable'}
                        </span>
                    </div>

                    <div className="mt-4 flex gap-2">
                        <Button variant="ghost" className="flex-1 py-2" onClick={() => onEdit(item)}>
                            <Edit size={16} />
                            Edit
                        </Button>
                        <Button variant="danger" className="flex-1 py-2" onClick={() => onRemove(item.itemId)}>
                            <Trash2 size={16} />
                            Remove
                        </Button>
                    </div>
                </div>
            ))}
        </div>
    );
}
