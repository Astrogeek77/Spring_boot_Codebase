import { useState } from "react";

import {
    Container, Typography, Box, TextField,
    InputLabel, MenuItem, FormControl, Select, CircularProgress, Button
} from "@mui/material";
import axios from "axios";

function SEG_main() {
    const [emailContent, setEmailContent] = useState('');
    const [tone, setTone] = useState('');
    const [generatedReply, setGeneratedReply] = useState('');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');

    const toneMenuItems = [
        { label: "Proffessional", value: "proffessional" },
        { label: "Casual", value: "casual" },
        { label: "Friendly", value: "friendly" }
    ];

    const handleSubmit = async () => {
        setLoading(true);
        setError('');
        try {
            const response = await axios.post("http://localhost:8080/api/email/generate", {
                emailContent,
                tone
            });

            setGeneratedReply(typeof response.data === 'string' ? response.data : JSON.stringify(response.data))
        } catch (error) {
            setError("Failed to generate reply to internal error. Please try again.");
             console.error(error);
        }
        finally {
            setLoading(false);
        }
    }

    return (
        <>
            <Container maxWidth="md" sx={{ py: 4 }}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Smart Email Reply Generator
                </Typography>

                <Box sx={{ mx: 3 }}>
                    <TextField
                        fullWidth
                        mutliline='true'
                        rows={5}
                        variant='outlined'
                        label="Original Email Content"
                        value={emailContent || ''}
                        onChange={e => setEmailContent(e.target.value)}
                        sx={{ mb: 2 }}
                    />

                    <FormControl fullWidth sx={{ mb: 2 }}>
                        <InputLabel>Tone (Optional)</InputLabel>
                        <Select
                            value={tone || ''}
                            label="Tone (Optional)"
                            onChange={e => setTone(e.target.value)}>
                            <MenuItem value="">None</MenuItem>
                            {toneMenuItems.map(item => (
                                // {console.log(item)}
                                <MenuItem key={item.value} value={item.value}>{item.label}</MenuItem>
                            ))}
                        </Select>
                    </FormControl>

                    <Button variant="contained" onClick={handleSubmit} disabled={!emailContent || loading}>
                        {loading ? <CircularProgress size={24} /> : "Generate smart reply"}
                    </Button>
                </Box>

                {error && <Typography color="error" sx={{ mt: 2 }}>
                    {error}
                </Typography>}

                {generatedReply && (
                    <Box sx={{ mt: 3 }}>
                        <Typography variant="h6" gutterBottom>
                            Genrated Reply
                        </Typography>
                        <TextField
                            fullWidth
                            multiline
                            rows={6}
                            variant="outlined"
                            value={generatedReply || ''}
                            inputProps={{ readOnly: true }} />

                        <Button
                            variant='outlined'
                            sx={{ mt: 2 }}
                            onClick={() => navigator.clipboard.writeText(generatedReply)} >
                            Copy to Clipboard
                        </Button>
                    </Box>
                )}

                {/* <p>{emailContent, tone, generatedReply, loading, error}</p> */}
            </Container>
        </>
    )
}

export default SEG_main;