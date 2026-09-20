import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';
import { Dumbbell, Plus, LogOut, X, Loader2, Search } from 'lucide-react';
import './Dashboard.css';
import './Exercises.css';

const MUSCLE_GROUPS = ['All', 'Chest', 'Back', 'Legs', 'Shoulders', 'Arms', 'Core'];

export default function Exercises() {
  const { user, logout } = useAuth();
  const [exercises, setExercises] = useState([]);
  const [loading, setLoading] = useState(true);
  const [selectedGroup, setSelectedGroup] = useState('All');
  const [modalOpen, setModalOpen] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  const [form, setForm] = useState({
    name: '',
    muscleGroup: 'Chest',
    description: '',
  });

  const fetchExercises = async () => {
    try {
      setLoading(true);
      const res = await api.get('/exercises');
      setExercises(res.data);
    } catch (err) {
      console.error('Failed to load exercises:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchExercises();
  }, []);

  const handleCreateExercise = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    try {
      await api.post('/exercises', form);
      setModalOpen(false);
      setForm({ name: '', muscleGroup: 'Chest', description: '' });
      fetchExercises();
    } catch (err) {
      console.error('Failed to save exercise:', err);
      alert('Could not save exercise.');
    } finally {
      setSubmitting(false);
    }
  };

  const filteredExercises = exercises.filter((ex) => {
    if (selectedGroup === 'All') return true;
    const group = ex.muscleGroup || ex.category || '';
    return group.toLowerCase() === selectedGroup.toLowerCase();
  });

  return (
    <div className="exercises-container">
      {/* Top Navbar */}
      <header className="navbar">
        <div style={{ display: 'flex', alignItems: 'center', gap: '2rem' }}>
          <div className="nav-brand">
            <Dumbbell size={24} />
            <span>Gym Tracker</span>
          </div>
          <nav className="nav-links">
            <Link to="/dashboard" className="nav-link">Workouts</Link>
            <Link to="/exercises" className="nav-link active">Exercises</Link>
          </nav>
        </div>
        <div className="nav-actions">
          <span className="user-badge">@{user?.userName || 'Lifter'}</span>
          <button onClick={logout} className="logout-btn">
            <LogOut size={16} /> Logout
          </button>
        </div>
      </header>

      {/* Content */}
      <main className="main-content">
        <div className="content-header">
          <div>
            <h2 className="content-title">Exercise Library</h2>
          </div>
          <button onClick={() => setModalOpen(true)} className="primary-btn">
            <Plus size={18} /> New Exercise
          </button>
        </div>

        {/* Filter Pills */}
        <div className="exercise-filter-bar">
          {MUSCLE_GROUPS.map((group) => (
            <button
              key={group}
              onClick={() => setSelectedGroup(group)}
              className={`filter-chip ${selectedGroup === group ? 'active' : ''}`}
            >
              {group}
            </button>
          ))}
        </div>

        {loading ? (
          <div className="loading-box">
            <Loader2 size={32} className="spinner-icon" />
          </div>
        ) : filteredExercises.length === 0 ? (
          <div className="empty-state">
            <Dumbbell size={40} className="empty-state-icon" />
            <p>No exercises found for <strong>{selectedGroup}</strong>. Add one above!</p>
          </div>
        ) : (
          <div className="exercise-grid">
            {filteredExercises.map((ex) => (
              <div key={ex.id} className="exercise-card">
                <div className="exercise-header">
                  <h3 className="exercise-title">{ex.name}</h3>
                  <span className="muscle-badge">
                    {ex.muscleGroup || ex.category || 'General'}
                  </span>
                </div>
                <p className="exercise-desc">
                  {ex.description || 'Targeted resistance training movement.'}
                </p>
              </div>
            ))}
          </div>
        )}
      </main>

      {/* New Exercise Modal */}
      {modalOpen && (
        <div className="modal-backdrop">
          <div className="modal-card">
            <div className="modal-header">
              <h3 className="modal-title">Create Exercise</h3>
              <button onClick={() => setModalOpen(false)} className="close-btn">
                <X size={20} />
              </button>
            </div>

            <form onSubmit={handleCreateExercise} className="modal-form">
              <div className="form-group">
                <label>Exercise Name</label>
                <input
                  type="text"
                  placeholder="e.g., Incline Dumbbell Press"
                  required
                  value={form.name}
                  onChange={(e) => setForm({ ...form, name: e.target.value })}
                />
              </div>

              <div className="form-group">
                <label>Target Muscle Group</label>
                <select
                  value={form.muscleGroup}
                  onChange={(e) => setForm({ ...form, muscleGroup: e.target.value })}
                >
                  {MUSCLE_GROUPS.filter((g) => g !== 'All').map((g) => (
                    <option key={g} value={g}>{g}</option>
                  ))}
                </select>
              </div>

              <div className="form-group">
                <label>Description / Form Notes</label>
                <input
                  type="text"
                  placeholder="e.g., Keep elbows at 45 degrees, pause at the bottom"
                  value={form.description}
                  onChange={(e) => setForm({ ...form, description: e.target.value })}
                />
              </div>

              <button type="submit" disabled={submitting} className="primary-btn submit-btn">
                {submitting ? <Loader2 size={18} className="spinner-icon" /> : 'Save to Library'}
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}