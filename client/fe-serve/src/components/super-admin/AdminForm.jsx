import { useState } from "react";
import Button from "../common/Button";
import InputField from "../common/InputField";
import * as superAdminApi from "../../api/superAdminApi";

export default function AdminForm({ restaurantId, onAdminCreated }) {
    const [username, setUsername] = useState("");
    const [emailAddress, setEmailAddress] = useState("");
    const [password, setPassword] = useState("");
    const [contactNumber, setContactNumber] = useState("");
    const [address, setAddress] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError("");

        try {
            const payload = {
                username,
                emailAddress,
                password,
                contactNumber,
                address,
                restaurantId
            };
            const response = await superAdminApi.createAdmin(payload);
            onAdminCreated(response.data.data);
        } catch (err) {
            console.error("Failed to create admin:", err);
            setError(err.response?.data?.message || "Failed to create admin");
        } finally {
            setLoading(false);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            {error && <div className="text-red-500 text-sm mb-3">{error}</div>}
            
            <InputField 
                placeholder="Admin Username" 
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
            />
            
            <InputField 
                placeholder="Admin Email" 
                className="mt-3" 
                type="email"
                value={emailAddress}
                onChange={(e) => setEmailAddress(e.target.value)}
                required
            />
            
            <InputField 
                placeholder="Password" 
                type="password" 
                className="mt-3" 
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
            />
            
            <InputField 
                placeholder="Contact Number" 
                className="mt-3" 
                value={contactNumber}
                onChange={(e) => setContactNumber(e.target.value)}
                required
            />
            
            <InputField 
                placeholder="Address" 
                className="mt-3" 
                value={address}
                onChange={(e) => setAddress(e.target.value)}
            />

            <Button variant="success" type="submit" className="mt-4 w-full" disabled={loading}>
                {loading ? "Creating..." : "Create Admin"}
            </Button>
        </form>
    );
}
