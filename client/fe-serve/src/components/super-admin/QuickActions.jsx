import { Plus, Upload, Download } from "lucide-react";
import { useRef } from "react";
import Button from "../common/Button";

export default function QuickActions({ onCreateProperty, onAddFood, onAddCategory, onUploadCsv }) {
    const fileInputRef = useRef(null);

    const handleFileChange = (e) => {
        const file = e.target.files[0];
        if (file && onUploadCsv) {
            onUploadCsv(file);
        }
        e.target.value = null; // reset to allow uploading the same file again
    };

    const handleDownloadFormat = () => {
        const csvContent = "Name,CategoryName,FoodType,DefaultImage\nSample Item,Sample Category,VEG,";
        const blob = new Blob([csvContent], { type: 'text/csv' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'food_items_format.csv';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        URL.revokeObjectURL(url);
    };

    return (
        <div className="mt-6 flex flex-col gap-3 sm:flex-row">
            <Button onClick={onCreateProperty}>
                <Plus size={18} />
                Onboard Admin
            </Button>

            <Button variant="warning" onClick={onAddFood}>
                <Plus size={18} />
                Add Food Item
            </Button>

            <input 
                type="file" 
                accept=".csv" 
                ref={fileInputRef} 
                onChange={handleFileChange} 
                className="hidden" 
            />
            
            <Button variant="dark" onClick={() => fileInputRef.current?.click()}>
                <Upload size={18} />
                Upload CSV
            </Button>

            <Button variant="indigo" onClick={handleDownloadFormat}>
                <Download size={18} />
                CSV Format
            </Button>

            <Button variant="success" onClick={onAddCategory}>
                <Plus size={18} />
                Add Category
            </Button>
        </div>
    );
}
