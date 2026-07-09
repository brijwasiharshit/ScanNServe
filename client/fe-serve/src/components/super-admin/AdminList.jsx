import { Trash2 } from "lucide-react";
import Button from "../common/Button";

export default function AdminList({ admins }) {
    return (
        <div className="mt-6 grid gap-4">
            {admins.map((admin) => (
                <div
                    key={admin.id}
                    className="rounded-2xl border border-[#E2E8F0] bg-white p-5"
                >
                    <h3 className="text-lg font-semibold">{admin.property}</h3>
                    <p className="text-slate-500">{admin.email}</p>

                    <span className="mt-2 inline-block rounded-full bg-green-100 px-3 py-1 text-sm text-green-700">
                        {admin.status}
                    </span>

                    <div className="mt-4 flex gap-3">
                        <Button className="rounded-lg px-4 py-2">Edit</Button>

                        <Button variant="danger" className="rounded-lg px-4 py-2">
                            <Trash2 size={16} />
                            Soft Delete
                        </Button>
                    </div>
                </div>
            ))}
        </div>
    );
}
