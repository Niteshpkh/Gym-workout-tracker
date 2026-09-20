import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/axios';
import { useAuth } from '../context/AuthContext';
import { Dumbbell, Plus, LogOut, Calendar, Clock, X, Trash2, Loader2 } from 'lucide-react';
import './Dashboard.css';

export default function Dashboard() {
  const { user, logout } = useAuth();
  const [workouts, setWorkouts] = useState([]);
  const [exercises, setExercises] = useState([]);
  const [loading, setLoading] = useState(true);
  const [modalOpen, setModalOpen] = useState(false);
  const [submitting, setSubmitting] = useState(false);

  // Form State matching WorkoutRequest DTO
  const [workoutForm, setWorkoutForm] = useState({
    name: '',
    date: new Date().toISOString().split('T')[0],
    duration: 45,
    exercises: [
      {
        exerciseId: '',
        sets: [{ reps: 10, weight: 60 }],
      },
    ],
  });

  const fetchData = async () => {
    try {
      setLoading(true);
      const [workoutsRes, exercisesRes] = await Promise.all([
        api.get('/workouts/my-workouts'),
        api.get('/exercises'),
      ]);
      setWorkouts(workoutsRes.data);
      setExercises(exercisesRes.data);

      if (exercisesRes.data.length > 0) {
        setWorkoutForm((prev) => ({
          ...prev,
          exercises: [{ exerciseId: exercisesRes.data[0].id, sets: [{ reps: 10, weight: 60 }] }],
        }));
      }
    } catch (err) {
      console.error('Failed to load dashboard data:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  const handleAddExerciseRow = () => {
    setWorkoutForm({
      ...workoutForm,
      exercises: [
        ...workoutForm.exercises,
        {
          exerciseId: exercises[0]?.id || '',
          sets: [{ reps: 10, weight: 60 }],
        },
      ],
    });
  };

  const handleRemoveExerciseRow = (index) => {
    const updated = workoutForm.exercises.filter((_, i) => i !== index);
    setWorkoutForm({ ...workoutForm, exercises: updated });
  };

  const handleAddSetRow = (exerciseIndex) => {
    const updated = [...workoutForm.exercises];
    updated[exerciseIndex].sets.push({ reps: 10, weight: 60 });
    setWorkoutForm({ ...workoutForm, exercises: updated });
  };

  const handleRemoveSetRow = (exerciseIndex, setIndex) => {
    const updated = [...workoutForm.exercises];
    updated[exerciseIndex].sets = updated[exerciseIndex].sets.filter((_, i) => i !== setIndex);
    setWorkoutForm({ ...workoutForm, exercises: updated });
  };

  const handleSubmitWorkout = async (e) => {
    e.preventDefault();
    setSubmitting(true);
    try {
      await api.post('/workouts', workoutForm);
      setModalOpen(false);
      fetchData();
    } catch (err) {
      console.error('Failed to record workout:', err);
      alert('Could not save workout. Make sure all fields are filled.');
    } finally {
      setSubmitting(false);
    }
  };

  const getExerciseName = (exerciseId) => {
    const found = exercises.find((ex) => ex.id === exerciseId);
    return found ? found.name : 'Exercise';
  };

  return (
    <div className="dashboard-container">
      {/* Top Navbar */}
      <header className="navbar">
        <div className="nav-left">
          <div className="nav-brand">
            <Dumbbell size={24} />
            <span>Gym Tracker</span>
          </div>
          <nav className="nav-links">
            <Link to="/dashboard" className="nav-link active">Workouts</Link>
            <Link to="/exercises" className="nav-link">Exercises</Link>
          </nav>
        </div>
        <div className="nav-actions">
          <span className="user-badge">@{user?.userName || 'Lifter'}</span>
          <button onClick={logout} className="logout-btn">
            <LogOut size={16} /> Logout
          </button>
        </div>
      </header>

      {/* Main Content */}
      <main className="main-content">
        <div className="content-header">
          <div>
            <h2 className="content-title">Workout Log</h2>
          </div>
          <button onClick={() => setModalOpen(true)} className="primary-btn">
            <Plus size={18} /> Log Workout
          </button>
        </div>

        {loading ? (
          <div className="loading-box">
            <Loader2 size={32} className="spinner-icon" />
          </div>
        ) : workouts.length === 0 ? (
          <div className="empty-state">
            <Dumbbell size={40} className="empty-state-icon" />
            <p>No workouts recorded yet. Click <strong>"Log Workout"</strong> to log your first session!</p>
          </div>
        ) : (
          <div className="workout-grid">
            {workouts.map((workout) => (
              <div key={workout.id} className="workout-card">
                <div className="card-header">
                  <h3 className="card-title">{workout.name}</h3>
                  <div className="card-meta">
                    <span className="meta-item">
                      <Calendar size={14} /> {workout.date}
                    </span>
                    <span className="meta-item">
                      <Clock size={14} /> {workout.duration} mins
                    </span>
                  </div>
                </div>

                {workout.exercises?.map((item, idx) => (
                  <div key={idx} className="exercise-block">
                    <div className="exercise-name">{getExerciseName(item.exerciseId)}</div>
                    <div className="sets-row">
                      {item.sets?.map((s, sIdx) => (
                        <span key={sIdx} className="set-chip">
                          Set {sIdx + 1}: <strong>{s.weight} kg</strong> × {s.reps} reps
                        </span>
                      ))}
                    </div>
                  </div>
                ))}
              </div>
            ))}
          </div>
        )}
      </main>

      {/* Log Workout Modal */}
      {modalOpen && (
        <div className="modal-backdrop">
          <div className="modal-card">
            <div className="modal-header">
              <h3 className="modal-title">Log Workout Session</h3>
              <button onClick={() => setModalOpen(false)} className="close-btn">
                <X size={20} />
              </button>
            </div>

            <form onSubmit={handleSubmitWorkout} className="modal-form">
              <div className="form-group">
                <label>Workout Name</label>
                <input
                  type="text"
                  placeholder="e.g., Heavy Push Day"
                  required
                  value={workoutForm.name}
                  onChange={(e) => setWorkoutForm({ ...workoutForm, name: e.target.value })}
                />
              </div>

              <div className="form-row">
                <div className="form-group">
                  <label>Date</label>
                  <input
                    type="date"
                    required
                    value={workoutForm.date}
                    onChange={(e) => setWorkoutForm({ ...workoutForm, date: e.target.value })}
                  />
                </div>
                <div className="form-group">
                  <label>Duration (minutes)</label>
                  <input
                    type="number"
                    min="1"
                    required
                    value={workoutForm.duration}
                    onChange={(e) => setWorkoutForm({ ...workoutForm, duration: Number(e.target.value) })}
                  />
                </div>
              </div>

              <hr className="modal-divider" />

              <div>
                <label className="section-label">Exercises & Sets</label>
                {workoutForm.exercises.map((exRow, exIndex) => (
                  <div key={exIndex} className="exercise-input-group">
                    <div className="exercise-select-row">
                      <select
                        className="exercise-select"
                        value={exRow.exerciseId}
                        onChange={(e) => {
                          const updated = [...workoutForm.exercises];
                          updated[exIndex].exerciseId = e.target.value;
                          setWorkoutForm({ ...workoutForm, exercises: updated });
                        }}
                      >
                        {exercises.map((ex) => (
                          <option key={ex.id} value={ex.id}>
                            {ex.name} ({ex.category || ex.muscleGroup || 'General'})
                          </option>
                        ))}
                      </select>
                      {workoutForm.exercises.length > 1 && (
                        <button type="button" onClick={() => handleRemoveExerciseRow(exIndex)} className="remove-btn">
                          <Trash2 size={16} />
                        </button>
                      )}
                    </div>

                    {exRow.sets.map((setRow, setIndex) => (
                      <div key={setIndex} className="set-input-row">
                        <input
                          type="number"
                          placeholder="Weight (kg)"
                          value={setRow.weight}
                          onChange={(e) => {
                            const updated = [...workoutForm.exercises];
                            updated[exIndex].sets[setIndex].weight = Number(e.target.value);
                            setWorkoutForm({ ...workoutForm, exercises: updated });
                          }}
                        />
                        <input
                          type="number"
                          placeholder="Reps"
                          value={setRow.reps}
                          onChange={(e) => {
                            const updated = [...workoutForm.exercises];
                            updated[exIndex].sets[setIndex].reps = Number(e.target.value);
                            setWorkoutForm({ ...workoutForm, exercises: updated });
                          }}
                        />
                        {exRow.sets.length > 1 && (
                          <button
                            type="button"
                            onClick={() => handleRemoveSetRow(exIndex, setIndex)}
                            className="remove-btn"
                          >
                            <Trash2 size={14} />
                          </button>
                        )}
                      </div>
                    ))}

                    <button type="button" onClick={() => handleAddSetRow(exIndex)} className="add-sub-btn">
                      + Add Set
                    </button>
                  </div>
                ))}

                <button type="button" onClick={handleAddExerciseRow} className="add-sub-btn add-exercise-btn">
                  + Add Another Exercise
                </button>
              </div>

              <button type="submit" disabled={submitting} className="primary-btn submit-btn">
                {submitting ? <Loader2 size={18} className="spinner-icon" /> : 'Save Workout'}
              </button>
            </form>
          </div>
        </div>
      )}
    </div>
  );
}